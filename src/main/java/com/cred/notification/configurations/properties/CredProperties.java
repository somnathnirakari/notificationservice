package com.cred.notification.configurations.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "cred.notifications", ignoreUnknownFields = true)
public class CredProperties {

    @NestedConfigurationProperty
    private PhoneVerification phoneVerification;

    @NestedConfigurationProperty
    private Sms sms;

    @NestedConfigurationProperty
    private PushNotification pushNotification;

}
