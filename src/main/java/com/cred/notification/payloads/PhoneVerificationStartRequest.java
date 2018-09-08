package com.cred.notification.payloads;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneVerificationStartRequest {

    @NotNull
    private String phoneNumber;
    @NotNull
    private String countryCode;
    @NotNull
    private DeliveryMode via;

    public PhoneVerificationStartRequest(@NotNull String phoneNumber, @NotNull String countryCode, @NotNull DeliveryMode via) {
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.via = via;
    }
}
