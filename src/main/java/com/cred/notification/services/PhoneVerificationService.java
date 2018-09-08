package com.cred.notification.services;

import com.cred.notification.payloads.DeliveryMode;

import java.util.Locale;

public interface PhoneVerificationService {

    void start(String countryCode, String phoneNumber, DeliveryMode via, Locale locale);

    void verify(String countryCode, String phoneNumber, String token, Locale locale);
}
