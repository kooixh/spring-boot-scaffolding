package com.kooixiuhong.api.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Credentials {
    private String username;
    private String password;
    private String accessToken;
}
