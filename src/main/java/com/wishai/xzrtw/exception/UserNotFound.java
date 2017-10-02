package com.wishai.xzrtw.exception;


public class UserNotFound extends Exception {

    private String errorInfo;

    public UserNotFound() {
        System.out.println("UserNotFound");
    }

    public UserNotFound(String msg) {
        errorInfo = msg;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}

