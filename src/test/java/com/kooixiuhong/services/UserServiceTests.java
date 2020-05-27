package com.kooixiuhong.services;

import com.kooixiuhong.api.common.exceptions.ExampleException;
import com.kooixiuhong.api.user.dtos.requests.UserCreationRequest;
import com.kooixiuhong.api.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("local")
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldRetrieveUser() {
        assertNotNull(userService.retrieveUser(1L));
    }

    @Test
    void shouldCreateUser() {
        assertEquals(1L, userService.createUser(new UserCreationRequest("name", 1, "test")));
    }

    @Test
    void shouldCatchError() {
        assertThrows(ExampleException.class, () -> {
            userService.createUser(new UserCreationRequest("name", -1, "test"));
        });
    }

}
