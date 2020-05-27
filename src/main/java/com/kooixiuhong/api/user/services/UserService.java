package com.kooixiuhong.api.user.services;

import com.kooixiuhong.api.common.exceptions.ErrorCodes;
import com.kooixiuhong.api.common.exceptions.ExampleException;
import com.kooixiuhong.api.user.dtos.pojos.User;
import com.kooixiuhong.api.user.dtos.requests.UserCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Long createUser(UserCreationRequest userCreationRequest) {
        if (userCreationRequest.getAge() < 0) {
            throw new ExampleException(ErrorCodes.INVALID_REQUEST, "age is not valid");
        }
        return 1L;
    }

    public User retrieveUser(long userId) {
        logger.debug("retrieving user {}", userId);
        return new User();
    }

}
