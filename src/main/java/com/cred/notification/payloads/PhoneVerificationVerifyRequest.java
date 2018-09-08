package com.cred.notification.payloads;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneVerificationVerifyRequest {
    @NotNull
    private String phoneNumber;
    @NotNull
    private String countryCode;
    @NotNull
    private String token;

    public PhoneVerificationVerifyRequest(@NotNull String phoneNumber, @NotNull String countryCode, @NotNull String token) {
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.token = token;
    }
}
