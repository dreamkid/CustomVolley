package com.hezd.customvolley;

import com.android.volley.RequestType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hezd on 2016/4/20.
 */
public interface VolleyRequestUrl {
    public static final String BASIC_URL = "http://192.168.1.130/jiahe_base";

    /**
     * 网络请求枚举类
     * 规范
     * get请求：GET_XXX
     * post请求：POST_XXX
     */
    public static enum Action implements RequestType {
        /**2.33二维码验票接口*/
        GET_APP_QRVERIFYT_ICKET,
    }

    /**
     * 请求集合
     */
    public static final Map<Action,String> URL_LIST = new HashMap<Action,String>(){
        {
            put(Action.GET_APP_QRVERIFYT_ICKET,"http://10.31.8.113:9999/m/shares/egoods/get");
//            put(Action.GET_APP_QRVERIFYT_ICKET,BASIC_URL+"/orderInfo/appQrVerifyTicket.do");
        }
    };
}
