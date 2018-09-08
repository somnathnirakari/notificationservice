package com.cred.notification.configurations.properties;

import lombok.Data;

@Data
class TwilioSMS {

    private String sid;
    private String accountSid;
    private String authToken;
}

