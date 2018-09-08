package com.cred.notification.configurations.properties;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
class Sms {

    @NestedConfigurationProperty
    private TwilioSMS twilio;
}
