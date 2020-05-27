package com.kooixiuhong.api.common.auth;

import com.kooixiuhong.api.common.dtos.Credentials;
import com.kooixiuhong.api.common.exceptions.ErrorCodes;
import com.kooixiuhong.api.common.exceptions.ExampleException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("userAuthenticator")
public class UsernameAuthenticator implements Authenticator<Credentials> {

    public boolean authenticate(Credentials credentials) {
        boolean authenticated = true;
        if (!authenticated) {
            throw new ExampleException(ErrorCodes.UNAUTHORIZED, "not authenticated");
        }
        return true;
    }
}
