package com.kooixiuhong.api.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Component
public class RequestUtils {

    public static final String UUID_HEADER_FIELD = "correlationId";

    private RequestUtils() {}

    public static String setRequestUUID(HttpServletRequest request, HttpServletResponse response) {
        String uniqueRequestId;
        Object uuidAttribute = request.getHeader(UUID_HEADER_FIELD);
        if (uuidAttribute == null) {
            uniqueRequestId = UUID.randomUUID().toString();
            request.setAttribute(UUID_HEADER_FIELD, uniqueRequestId);
        } else {
            uniqueRequestId = (String) uuidAttribute;
        }
        request.setAttribute(UUID_HEADER_FIELD, uniqueRequestId);
        response.setHeader(UUID_HEADER_FIELD, uniqueRequestId);
        return uniqueRequestId;
    }


    public static String getRequestUUID(HttpServletRequest request) {
        return (String) request.getAttribute(UUID_HEADER_FIELD);
    }

    public static String getCurrentCorrelationId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return (String) ((ServletRequestAttributes)requestAttributes).getRequest().getAttribute(UUID_HEADER_FIELD);
        }
        return "";
    }

}
