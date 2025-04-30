package com.courier.sgacourierapp.models.response;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class VerifyResponse<T> {
    private String responseCode;
    private String responseMessage;
    private T data;
}
