package com.kooixiuhong.api.common.interceptors;

import com.kooixiuhong.api.common.constants.LoggingConstant;
import com.kooixiuhong.api.common.utils.RequestUtils;
import net.logstash.logback.marker.LogstashMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static net.logstash.logback.argument.StructuredArguments.value;
import static net.logstash.logback.marker.Markers.append;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    private static final String START_TIME_HEADER_FIELD = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        long startTime = System.currentTimeMillis();
        String uniqueRequestId = RequestUtils.setRequestUUID(request, response);

        logger.debug("Handling {} request uri: {} for correlationId {} at time {}",
                value(LoggingConstant.HTTP_METHOD, request.getMethod()),
                value(LoggingConstant.REQUEST_URI, requestUri),
                value(RequestUtils.UUID_HEADER_FIELD, uniqueRequestId),
                startTime);

        request.setAttribute(START_TIME_HEADER_FIELD, startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestUri = request.getRequestURI();
        String uniqueRequestId = RequestUtils.getRequestUUID(request);
        long startTime = (Long) request.getAttribute(START_TIME_HEADER_FIELD);
        long diff = System.currentTimeMillis() - startTime;

        LogstashMarker markers = append(LoggingConstant.REQUEST_BODY, request.getAttribute(LoggingConstant.REQUEST_BODY))
                .and(append(LoggingConstant.ENDPOINT_NAME, request.getAttribute(LoggingConstant.ENDPOINT_NAME)));

        logger.info(
                markers,
                "Completed handling {} request uri: {} returned {} for correlationId {} in {} ms",
                value(LoggingConstant.HTTP_METHOD, request.getMethod()),
                value(LoggingConstant.REQUEST_URI, requestUri),
                value(LoggingConstant.HTTP_CODE, response.getStatus()),
                value(RequestUtils.UUID_HEADER_FIELD, uniqueRequestId),
                value(LoggingConstant.RESPONSE_TIME, diff));
    }

}
