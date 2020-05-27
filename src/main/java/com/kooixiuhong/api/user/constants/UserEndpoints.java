package com.kooixiuhong.api.user.constants;

import com.kooixiuhong.api.common.constants.APIConstant;

public class UserEndpoints {

    private UserEndpoints() {}

    private static final String BASE_ENDPOINT = APIConstant.API_BASE + "api/";

    public static final String USER_CREATE_ENDPOINT = BASE_ENDPOINT + "user";
    public static final String USER_RETRIEVE_ENDPOINT = BASE_ENDPOINT + "user/{userId}";

}
