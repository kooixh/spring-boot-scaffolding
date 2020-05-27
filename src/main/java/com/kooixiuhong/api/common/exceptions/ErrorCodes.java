package com.kooixiuhong.api.common.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {


    UNAUTHORIZED(3004, "User details is not valid", HttpStatus.UNAUTHORIZED.value()),
    INVALID_HEADERS(3008, "Auth api internal server error", HttpStatus.BAD_REQUEST.value()),
    INVALID_REQUEST(1001, "Invalid Request Body", HttpStatus.BAD_REQUEST.value());

    private int errorCode;
    private String errorMessage;
    private int httpStatus;

    ErrorCodes(int errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
