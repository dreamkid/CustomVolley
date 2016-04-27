package com.hezd.customvolley;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.hezd.customvolley.VolleyRequestUrl.Action;

public class VolleyRequest<T> extends Request<T> {

    private final String CODE = "CODE";
    private final String DATA = "DATA";
    private final String MSG = "MSG";
    private final String RESULT_SUCCESS = "200";
    private final Response.Listener mListener;
    private final VolleyRequestUrl.Action mAction;
    private final Response.ErrorListener mErrorListener;
    private Class<T> mClass;

    public VolleyRequest(Action action, int method, Map<String,String> params, Class<T> clazz, Response.Listener<List<T>> listener, Response.ErrorListener errorListener) {
        super(action,method, spliceUrl(VolleyRequestUrl.URL_LIST.get(action),params), errorListener);
        this.mAction = action;
        this.mClass = clazz;
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(jsonString);
            String errCode = jsonObject.getString(CODE);
            String msg = jsonObject.getString(MSG);
            if (!RESULT_SUCCESS.equals(errCode)) {
                return Response.error(new VolleyError(errCode + msg));
            }
            if (jsonString.contains(DATA)) {
                String data = jsonObject.getString(DATA);
                // 部分成功回调DATA无数据
                if (mClass == null || TextUtils.isEmpty(data)) {
                    return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                }
                return Response.success(JSON.parseObject(data, mClass), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(mAction,error);
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(mAction,response);
    }


    private static String spliceUrl(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        } else {
            StringBuilder result = new StringBuilder();
            for (ConcurrentHashMap.Entry<String, String> entry : params.entrySet()) {
                if (result.length() > 0) {
                    result.append("&");
                }
                final String key = entry.getKey();
                final String value = entry.getValue();
                result.append(encodeUTF8(key));
                result.append("=");
                result.append(encodeUTF8(value));
            }
            return url + "?" + result.toString();
        }
    }

    /**
     * 对给定的字符串做 URLEncode，并把 encode 结果中的 "+" 全部替换成 "%20"。
     */
    public static String encodeUTF8(String value) {
        String result = "";
        try {
            result = URLEncoder.encode(value, "UTF-8");
            result = result.replaceAll("\\+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
