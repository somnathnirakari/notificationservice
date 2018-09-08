package com.cred.notification.configurations.properties;

import lombok.Data;

@Data
class TwilioPhoneVerification {

    private String apiKey;
    private int codeLength;
}

