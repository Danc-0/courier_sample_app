package com.courier.sgacourierapp.models.request;

@lombok.Data
@lombok.NoArgsConstructor(force = true)
@lombok.AllArgsConstructor
@lombok.Builder
public class VerifyRequest {
    private String personalID;
    private String verificationCode;
}
