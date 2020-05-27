package com.kooixiuhong.api.common.interceptors;

import static net.logstash.logback.argument.StructuredArguments.value;


import com.kooixiuhong.api.common.constants.APIConstant;
import com.kooixiuhong.api.common.responses.ErrorApiResponse;
import com.kooixiuhong.api.common.constants.LoggingConstant;
import com.kooixiuhong.api.common.exceptions.ErrorCodes;
import com.kooixiuhong.api.common.exceptions.ExampleException;
import com.kooixiuhong.api.common.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles all the exception in the application and sends appropriate response
 */
@ControllerAdvice
public class ExceptionHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerInterceptor.class);
    private static final int DEFAULT_ERROR = 9999;

    private static final String ERROR_MSG_TEMPLATE = "Error {} code {} for correlationId {}";

    @ExceptionHandler(ExampleException.class)
    @ResponseBody
    public ErrorApiResponse handleManagedException(HttpServletRequest request, HttpServletResponse response,
                                                   ExampleException ex) {

        logger.error(ERROR_MSG_TEMPLATE,
                value(LoggingConstant.ERROR_NAME, ex.getErrorCode()),
                value(LoggingConstant.ERROR_CODE, ex.getErrorCode().getErrorCode()),
                value(RequestUtils.UUID_HEADER_FIELD, RequestUtils.getCurrentCorrelationId()), ex);

        String errorURL = request.getRequestURL().toString();
        int code = ex.getErrorCode().getErrorCode();
        String errorMessage = ex.getMessage();
        response.setStatus(ex.getErrorCode().getHttpStatus());
        ErrorApiResponse errorApiResponse = new ErrorApiResponse(APIConstant.API_FAIL_STATUS, errorMessage, code, errorURL);

        request.setAttribute(LoggingConstant.RESPONSE_BODY, errorApiResponse);
        return errorApiResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ErrorApiResponse handleHttpMessageException(HttpServletRequest request, HttpServletResponse response,
                                                       HttpMessageNotReadableException ex) {

        logger.error(ERROR_MSG_TEMPLATE,
                value(LoggingConstant.ERROR_NAME, ErrorCodes.INVALID_REQUEST),
                value(LoggingConstant.ERROR_CODE, ErrorCodes.INVALID_REQUEST.getErrorCode()),
                value(RequestUtils.UUID_HEADER_FIELD, RequestUtils.getCurrentCorrelationId()));

        String errorURL = request.getRequestURL().toString();
        int code = ErrorCodes.INVALID_REQUEST.getErrorCode();
        String errorMessage = ErrorCodes.INVALID_REQUEST.getErrorMessage();
        response.setStatus(ErrorCodes.INVALID_REQUEST.getHttpStatus());
        ErrorApiResponse errorApiResponse = new ErrorApiResponse(APIConstant.API_FAIL_STATUS, errorMessage, code, errorURL);

        request.setAttribute(LoggingConstant.RESPONSE_BODY, errorApiResponse);
        return errorApiResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorApiResponse handleArgumentNotValidException(HttpServletRequest request, HttpServletResponse response,
                                                            MethodArgumentNotValidException ex) {

        logger.error(ERROR_MSG_TEMPLATE,
                value(LoggingConstant.ERROR_NAME, ErrorCodes.INVALID_REQUEST),
                value(LoggingConstant.ERROR_CODE, ErrorCodes.INVALID_REQUEST.getErrorCode()),
                value(RequestUtils.UUID_HEADER_FIELD, RequestUtils.getCurrentCorrelationId()));

        String errorURL = request.getRequestURL().toString();
        int code = ErrorCodes.INVALID_REQUEST.getErrorCode();
        String errorMessage = ex.getBindingResult().getFieldError().getField() + " contains invalid values.";
        response.setStatus(ErrorCodes.INVALID_REQUEST.getHttpStatus());
        ErrorApiResponse errorApiResponse = new ErrorApiResponse(APIConstant.API_FAIL_STATUS, errorMessage, code, errorURL);

        request.setAttribute(LoggingConstant.RESPONSE_BODY, errorApiResponse);
        return errorApiResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorApiResponse handleUnexpectedException(HttpServletRequest req, Exception ex) {

        logger.error(ERROR_MSG_TEMPLATE,
                value(LoggingConstant.ERROR_NAME, ex.getMessage()),
                value(LoggingConstant.ERROR_CODE, DEFAULT_ERROR),
                value(RequestUtils.UUID_HEADER_FIELD, RequestUtils.getCurrentCorrelationId()), ex);

        String errorURL = req.getRequestURL().toString();
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String errorMessage = ex.getMessage();
        ErrorApiResponse errorApiResponse = new ErrorApiResponse(APIConstant.API_ERROR_STATUS, errorMessage, code, errorURL);

        req.setAttribute(LoggingConstant.RESPONSE_BODY, errorApiResponse);
        return errorApiResponse;
    }

}
