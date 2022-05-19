package com.example.musicat.exception.customException;

public class InvalidNotifyException extends RuntimeException {
    public InvalidNotifyException(String message) { super(message); }
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_NOTIFY;
    }
}
