package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.models.request.LoginRequest;
import com.courier.sgacourierapp.models.request.VerifyRequest;
import com.courier.sgacourierapp.models.response.LoginResponse;
import com.courier.sgacourierapp.models.response.VerifyResponse;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class CourierAuthenticationService {

    private static Logger log = Logger.getLogger(CourierAuthenticationService.class.getName());

    public CourierAuthenticationService() {

    }

    /**
     * Unique fields should be
     * Email
     * Phone Number
     * ID Number
     * Personal User ID
     */
    public LoginResponse login(final LoginRequest registrationRequest) {
        log.info("CourierAuthenticationService-login request ");
        log.info("CourierAuthenticationService-login response ");
        return new LoginResponse();
    }

    public VerifyResponse verify(final VerifyRequest verifyRequest) {
        log.info("CourierAuthenticationService-verify request ");
        log.info("CourierAuthenticationService-verify response ");
        return new VerifyResponse();
    }


}
