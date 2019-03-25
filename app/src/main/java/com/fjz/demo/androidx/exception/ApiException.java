package com.fjz.demo.androidx.exception;

public class ApiException extends Exception {

    public ApiException() {
        super();
    }

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(int code) {
        super(getMsg(code));
    }

    public static String getMsg(int code) {

        String msg = "未知错误";
        switch (code) {
            case -1:
                msg = "非法的参数";
                break;

        }

        return msg;
    }
}
