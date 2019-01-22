/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.demo.common.base;

import com.alibaba.fastjson.JSON;
import com.github.fartherp.demo.common.enums.ErrorCodeEnum;
import com.github.fartherp.demo.common.req.PageReq;

public class JsonResp<T> {

    //成功（0），失败则由相关失败码
    private int result = 0;

    //相关的错误信息
    private String errMsg = "";

    private int totalCount;     //	option	int	数据总条数

    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setData(T value) {
        data = value;
    }

    public T getData() {
        return data;
    }

    public JsonResp pagination(PageReq req) {
        //总记录数
        this.setTotalCount(req.getTotal());
        return this;
    }

    /**
     * 设置响应失败标志.
     *
     * @param errorCodeEnum 消息.
     * @return 该响应对象.
     */
    public JsonResp errorMsg(ErrorCodeEnum errorCodeEnum) {
        if (errorCodeEnum == null) {
            errorCodeEnum = ErrorCodeEnum.Default;
        }
        setResult(errorCodeEnum.getCode());
        setErrMsg(errorCodeEnum.getDesc());
        return this;
    }

    /**
     * 设置响应失败标志.
     *
     * @param message 消息.
     * @return 该响应对象.
     */
    public JsonResp errorMsg(int result, String message) {
        setResult(result);
        setErrMsg(message);
        return this;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static <T> JsonResp data(T t) {
        JsonResp<T> resp = new JsonResp<>();
        resp.setData(t);
        return resp;
    }

    public static JsonResp data() {
        return new JsonResp<>();
    }
}
