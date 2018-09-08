package com.cred.notification.services;

import com.cred.notification.payloads.SendSMSRequest;

public interface SMSService {

    void send(SendSMSRequest sendSMSRequest);
}

