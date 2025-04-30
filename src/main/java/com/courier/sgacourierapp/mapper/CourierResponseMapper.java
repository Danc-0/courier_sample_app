package com.courier.sgacourierapp.mapper;

import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.models.response.Data;
import com.courier.sgacourierapp.models.response.LoginResponse;
import com.courier.sgacourierapp.models.response.VerifyResponse;
import org.springframework.stereotype.Component;

@Component
public class CourierResponseMapper {

    public LoginResponse mapToLoginResponse(final UserEntity userEntity) {
        final LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResponseCode("001");
        loginResponse.setResponseMessage("Successful");
        loginResponse.setData("");
        return loginResponse;
    }

    public VerifyResponse mapToData(final UserEntity userEntity) {
        final VerifyResponse verifyResponse = new VerifyResponse();
        verifyResponse.setResponseCode("001");
        verifyResponse.setResponseMessage("Successful");
        verifyResponse.setData("");
        return verifyResponse;
    }
}
