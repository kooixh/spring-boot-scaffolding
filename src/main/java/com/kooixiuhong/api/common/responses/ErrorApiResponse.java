package com.kooixiuhong.api.common.responses;

import lombok.Builder;
import lombok.Data;

@Data
public class ErrorApiResponse extends BaseAPIResponse {
    private int code;
    private String path;
    @Builder
    public ErrorApiResponse(String status, String message, int code, String path) {
        super(status, message);
        this.code = code;
        this.path = path;
    }
}
