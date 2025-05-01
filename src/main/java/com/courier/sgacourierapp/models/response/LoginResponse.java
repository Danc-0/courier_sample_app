package com.courier.sgacourierapp.models.response;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class LoginResponse<T> {
    private String responseCode;
    private String responseMessage;
    private boolean error;
    private String message;
    private String redirectUrl;
    private T data;
}
