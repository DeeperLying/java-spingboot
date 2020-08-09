package com.evan.wj.result;

public class Result {
    // 响应码
    private int code;

    // 消息
    private String message;

    // 响应对象
    private Object data;

    private Result (int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(int code, String message, Object data) {
        return new Result(code, message, data);
    }

    public static Result success() {
        return new Result(200, "响应成功", null);
    }

    public static Result success(Object data) {
        return new Result(200, "响应成功", data);
    }

    public static Result error(Object data) {
        return new Result(500, "响应错误", data);
    }

    public static Result error(int code, String message, Object data) {
        return new Result(code, message, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
