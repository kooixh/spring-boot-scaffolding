package com.kooixiuhong.api.healthcheck;

import com.kooixiuhong.api.common.constants.APIConstant;
import com.kooixiuhong.api.common.responses.BaseAPIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    /**
     * Returns a 200 response at the '/' path. Check if service is running
     *
     * @return
     */
    @GetMapping(value = {"/", APIConstant.API_BASE + "/health"})
    public ResponseEntity<BaseAPIResponse> getHealthCheck() {
        return new ResponseEntity<>(new BaseAPIResponse("OK", "api is running."), HttpStatus.OK);
    }

}
