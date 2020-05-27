package com.kooixiuhong.controllers;

import com.kooixiuhong.api.common.auth.Authenticator;
import com.kooixiuhong.api.common.dtos.Credentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("local")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("userAuthenticator")
    private Authenticator<Credentials> usernameAuthenticator;

    HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("username", "test-user");
        httpHeaders.add("password", "test-user-pass");
        return httpHeaders;
    }

    @BeforeEach
    void mockAuth() {
        Mockito.doReturn(true).when(usernameAuthenticator).authenticate(any());
    }

    @Test
    void shouldRetrieveUser() throws Exception {
        this.mockMvc.perform(get("/example/api/user/158875107500597160").headers(getHttpHeaders())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateUser() throws Exception {
        this.mockMvc.perform(post("/example/api/user/").headers(getHttpHeaders())
                .content("{\"name\": \"test\",\"age\": 10, \"email\": \"test@test.test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L));
    }

}
