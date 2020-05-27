package com.kooixiuhong.api.common.interceptors;

import com.kooixiuhong.api.common.auth.Authenticator;
import com.kooixiuhong.api.common.constants.AttributesConstants;
import com.kooixiuhong.api.common.constants.RequestKeysConstant;
import com.kooixiuhong.api.common.dtos.Credentials;
import com.kooixiuhong.api.common.exceptions.ErrorCodes;
import com.kooixiuhong.api.common.exceptions.ExampleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    @Qualifier("userAuthenticator")
    private Authenticator<Credentials> usernameAuthenticator;

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.debug("ssoAuthInterceptor, authenticating request using sso credentials");
        verifyHeaders(request);
        Credentials credentials = Credentials.builder()
                .username(request.getHeader(RequestKeysConstant.PASSWORD))
                .password(request.getHeader(RequestKeysConstant.USERNAME)).build();
        if (!usernameAuthenticator.authenticate(credentials)) {
            logger.debug("ssoAuthInterceptor, unable to authenticate request using sso credentials");
            throw new ExampleException(ErrorCodes.UNAUTHORIZED, "sso credentials provided is invalid");
        }
        logger.debug("ssoAuthInterceptor, successfully to authenticate request using sso credentials");
        request.setAttribute(AttributesConstants.AUTH_BY, AttributesConstants.AUTH_BY_USERNAME);
        request.setAttribute(AttributesConstants.CREDENTIALS, credentials);
        return true;

    }

    private void verifyHeaders(HttpServletRequest request) {
        if (Objects.isNull(request.getHeader(RequestKeysConstant.USERNAME))) {
            throw new ExampleException(ErrorCodes.INVALID_HEADERS, "username header is missing");
        }
        if (Objects.isNull(request.getHeader(RequestKeysConstant.PASSWORD))) {
            throw new ExampleException(ErrorCodes.INVALID_HEADERS, "password header is missing");
        }

    }
}
