package com.xupu.common.po;

public class ResultData {
    /**
     * http 请求的响应
     * @param status 响应状态
     * @param message 响应信息
     * @param json 响应数据
     */
    public ResultData(Status status, String message, String json) {
        this.status = status;
        this.message = message;
        this.json = json;
    }
    /**
     * 结果状态
     */
    private Status status;
    /**
     * 提示
     */
    private String message;
    /**
     * 返回的json 数据
     */
    private String json;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
