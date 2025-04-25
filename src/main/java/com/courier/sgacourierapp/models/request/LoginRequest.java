package com.courier.sgacourierapp.models.request;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class LoginRequest {
    final String personalID;
    final String password;
}
