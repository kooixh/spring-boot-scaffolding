package com.kooixiuhong.api.common.exceptions;

public class ExampleException extends RuntimeException {
    private final ErrorCodes errorCode;

    public ExampleException(ErrorCodes errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

}
