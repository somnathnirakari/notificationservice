package com.cred.notification.payloads;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterDeviceRequest {

    @NotNull
    String identity;
    @NotNull
    String address;
    @NotNull
    BindingType bindingType;

    public RegisterDeviceRequest(@NotNull String identity, @NotNull String address, @NotNull BindingType bindingType) {
        this.identity = identity;
        this.address = address;
        this.bindingType = bindingType;
    }
}
