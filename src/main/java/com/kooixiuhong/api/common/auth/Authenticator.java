package com.kooixiuhong.api.common.auth;

public interface Authenticator<T> {
    boolean authenticate(T credentials);
}
