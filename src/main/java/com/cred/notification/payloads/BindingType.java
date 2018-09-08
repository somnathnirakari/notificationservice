package com.cred.notification.payloads;

public enum BindingType {
    APN("apn"),
    FCM("fcm");

    private String bindingType;

    BindingType(String bindingType) {
        this.bindingType = bindingType;
    }
}
