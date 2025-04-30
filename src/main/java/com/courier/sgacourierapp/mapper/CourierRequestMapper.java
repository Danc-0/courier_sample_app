package com.courier.sgacourierapp.mapper;

import com.courier.sgacourierapp.models.request.LoginRequest;
import com.courier.sgacourierapp.models.request.VerifyRequest;
import org.springframework.stereotype.Component;

@Component
public class CourierRequestMapper {

    public LoginRequest mapToLoginRequest(final String userName, final String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPersonalID(userName);
        loginRequest.setPassword(password);
        return loginRequest;
    }

    public VerifyRequest mapToVerifyRequest(final String userName, final String verificationCode) {
        final VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setPersonalID(userName);
        verifyRequest.setVerificationCode(verificationCode);
        return verifyRequest;
    }

}
