# CustomVolley
为volley添加Action
需要创建一个枚举类定义请求类型实现RequestType接口请求时传入
当接口回调时 ，回调到统一入口然后根据action做分发处理
目的是为了保持代码结构清晰，容易维护。
类似于onclic方法 switch case做分发
用法
/**
     * 网络请求枚举类
     * 规范
     * get请求：GET_XXX
     * post请求：POST_XXX
     */
    public static enum Action implements RequestType{
    //定义具体网络请求类型，比如登录，注册……
    }
    
    网络请求
      Map<String,String> params = getDefaultParams();
      // 业务参数
      params.put("xxx", ooo);
      // 使用定义好的Action示例Action.xxx
      VolleyRequest<T> request = new VolleyRequest<>(Action.xxx, Request.Method.GET,params,clazz, listener, errorListener);
      addRequest(request);
    
    网络回调
    
    /**请求失败回调*/
    @Override
    public void onErrorResponse(RequestType requestType, VolleyError volleyError) {
        Action action = (Action) requestType;
        switch (action) {
            case xxx:
                LogUtil.d("hezd -->    failed");
                break;
            case ooo:
                LogUtil.d("hezd -->    failed");
                break;
        }
    }
    /**请求成功回调*/
    @Override
    public void onResponse(RequestType requestType, List<AppQrVerifyTicket> appQrVerifyTickets) {
        Action action = (Action) requestType;
        switch (action) {
            case xxx:
                LogUtil.d("hezd -->  sucess");
                break;
            case ooo:
                LogUtil.d("hezd -->  sucess");
                break;
        }
    }
