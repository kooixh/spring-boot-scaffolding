package com.kooixiuhong.api.user.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreationRequest {
    private String name;
    private int age;
    private String email;
}
