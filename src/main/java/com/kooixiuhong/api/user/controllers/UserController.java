package com.kooixiuhong.api.user.controllers;

import com.kooixiuhong.api.common.constants.LoggingConstant;
import com.kooixiuhong.api.user.constants.UserEndpoints;
import com.kooixiuhong.api.user.dtos.pojos.User;
import com.kooixiuhong.api.user.dtos.requests.UserCreationRequest;
import com.kooixiuhong.api.user.dtos.responses.UserCreationResponse;
import com.kooixiuhong.api.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = UserEndpoints.USER_RETRIEVE_ENDPOINT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> retrieveUser(HttpServletRequest servletRequest, @PathVariable Long userId) {

        logApiNameAndRequest(servletRequest, null, "Retrieve Order");

        User user = userService.retrieveUser(userId);

        servletRequest.setAttribute(LoggingConstant.RESPONSE_BODY, user);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = UserEndpoints.USER_CREATE_ENDPOINT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserCreationResponse> createUser(HttpServletRequest servletRequest, @RequestBody UserCreationRequest userCreationRequest) {

        logApiNameAndRequest(servletRequest, null, "Retrieve Order");

        Long userId = userService.createUser(userCreationRequest);

        UserCreationResponse response = new UserCreationResponse(userId);

        servletRequest.setAttribute(LoggingConstant.RESPONSE_BODY, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    private void logApiNameAndRequest(HttpServletRequest servletRequest, Object requestBody, String apiName) {
        servletRequest.setAttribute(LoggingConstant.REQUEST_BODY, requestBody);
        servletRequest.setAttribute(LoggingConstant.ENDPOINT_NAME, apiName);
    }
}
