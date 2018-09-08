package com.cred.notification.payloads;

public enum DeliveryMode {
    SMS("sms");

    private String deliveryMode;

    DeliveryMode(String deliveryMode) {
       this.deliveryMode = deliveryMode;
    }
}
