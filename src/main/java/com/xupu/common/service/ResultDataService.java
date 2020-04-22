package com.xupu.common.service;

import com.xupu.common.tools.Tool;
import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;

/**
 * http 响应结果处理
 */
public class ResultDataService {

    private static ResultDataService resultDataService;
    private ResultData errorResultData;
    private static ResultData successResultData;
    private ResultData otherResultData;
    private ResultData errMessageResultData;

    private ResultDataService() {
        errorResultData = new ResultData(Status.Error, "失败", null);
        successResultData = new ResultData(Status.Success, "成功", null);
        otherResultData = new ResultData(Status.Other, null, null);
        errMessageResultData = new ResultData(Status.Error,null,null);
    }


    /**
     * 单例模式 得到本对象
     * @return
     */
    public static ResultDataService getResultDataService() {
        if (resultDataService == null) {
            resultDataService = new ResultDataService();
        }
        return resultDataService;
    }
    /**
     * 得到错误的 http 返回信息
     *
     * @return
     */
    public ResultData getErrorResultData() {
        return errorResultData;
    }


    /**
     * 得到可以提示的信息
     *
     * @param message
     * @return
     */
    public ResultData getOtherResultData(String message) {

        otherResultData.setMessage(message);
        return otherResultData;
    }
    /**
     * 得到正确的 http 返回信息
     *
     * @param json 对象
     * @return
     */
    public ResultData getSuccessResultData(String json) {
        successResultData.setJson(json);
        return successResultData;
    }



    /**
     * 得到正确的 http 返回信息
     *
     * @param obj  被转换为json 的对象
     * @return
     */
    public ResultData getSuccessResultData(Object obj) {

        return getSuccessResultData(Tool.getGson().toJson(obj));
    }

    /**
     * 得到 自定义 的错误提示信息
     * @param tip
     * @return
     */
    public ResultData getErrorResultData(String tip) {
        errMessageResultData.setMessage(tip);
        return  errMessageResultData;
    }
}
