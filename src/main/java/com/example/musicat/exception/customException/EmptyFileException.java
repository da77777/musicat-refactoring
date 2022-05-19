package com.example.musicat.exception.customException;

public class EmptyFileException extends RuntimeException{
    public EmptyFileException(String message) { super(message); }
    public ErrorCode getErrorCode() {
        return ErrorCode.EMPTY_FILE;
    }
}
