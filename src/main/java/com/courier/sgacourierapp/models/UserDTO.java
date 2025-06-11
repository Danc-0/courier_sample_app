package com.courier.sgacourierapp.models;

import com.courier.sgacourierapp.common.CourierEnums.DispatchStatus;
import com.courier.sgacourierapp.common.CourierEnums.UserRoles;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String idNumber;
    private String gender;
    private String status;
    private String countryCode;
    private int isVerified;
    private int isActivated;
    private UserRoles role;
    private DispatchStatus dispatchStatus;
}
