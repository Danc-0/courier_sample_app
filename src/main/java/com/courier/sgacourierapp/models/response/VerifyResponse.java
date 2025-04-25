package com.courier.sgacourierapp.models.response;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class VerifyResponse {
    final String responseCode;
    final String responseMessage;
    final Data data;
}
