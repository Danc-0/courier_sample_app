package com.courier.sgacourierapp.models.response;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class LoginResponse<T> {
    String responseCode;
    String responseMessage;
    private T data;
}
