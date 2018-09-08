package com.cred.notification.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendSMSRequest {

    @NotNull
    private String phoneNumber;
    @NotNull
    private String countryCode;
    @NotNull
    private String message;

    public SendSMSRequest(@NotNull String phoneNumber, @NotNull String countryCode, @NotNull String message) {
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.message = message;
    }
}
