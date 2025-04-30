package com.courier.sgacourierapp.models.request;

import jakarta.validation.constraints.*;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class LoginRequest {

    @NotEmpty(message = "Enter a valid Id Number, Phone Number or Email")
    private String personalID;
    @NotEmpty(message = "Password is required")
    private String password;
}
