package com.courier.sgacourierapp.models.response;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class LoginResponse {
    final String responseCode;
    final String responseMessage;
    final Data data;
}
