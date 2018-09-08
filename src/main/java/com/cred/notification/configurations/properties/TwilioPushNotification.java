package com.cred.notification.configurations.properties;

import lombok.Data;

@Data
class TwilioPushNotification {

    private String apikey;
    private String apiSecret;
    private String accountSid;
    private String notificationServiceSid;
}

