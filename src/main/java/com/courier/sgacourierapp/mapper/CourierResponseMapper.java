package com.courier.sgacourierapp.mapper;

import com.courier.sgacourierapp.entities.CustomerEntity;
import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.models.CustomerDTO;
import com.courier.sgacourierapp.models.UserDTO;
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

    public CustomerDTO mapCustomerEntityToCustomerDTO(final CustomerEntity customerEntity) {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerType(customerEntity.getCustomerType());
        customerDTO.setAddress(customerEntity.getAddress());
        customerDTO.setStatus(customerEntity.getStatus());
        customerDTO.setEmail(customerEntity.getEmail());
        customerDTO.setPhoneNumber(customerEntity.getPhoneNumber());
        customerDTO.setPaymentTerms(customerEntity.getPaymentTerms());
        customerDTO.setUpdatedDate(customerEntity.getUpdatedDate());
        customerDTO.setCreatedDate(customerEntity.getCreatedDate());
        return customerDTO;
    }

    public UserDTO mapUserEntityToUser(final UserEntity userEntity) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setGender(userEntity.getGender());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setFullName(userEntity.getFullName());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userDTO.setIdNumber(userEntity.getIdNumber());
        userDTO.setRole(userEntity.getRole());
        userDTO.setDispatchStatus(userEntity.getDispatchStatus());
        userDTO.setIsActivated(userEntity.getIsActivated());
        userDTO.setStatus(userEntity.getStatus());
        userDTO.setCountryCode(userEntity.getCountryCode());
        return userDTO;
    }
}
