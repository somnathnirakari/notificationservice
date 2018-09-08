package com.cred.notification.services.impl;

import com.cred.notification.exceptions.PushNotificationException;
import com.cred.notification.payloads.BindingType;
import com.cred.notification.services.PushNotificationService;
import com.cred.notification.util.TwilioUtil;
import com.twilio.Twilio;
import com.twilio.rest.notify.v1.service.Binding;
import com.twilio.rest.notify.v1.service.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioPushNotificationServiceImpl implements PushNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioPushNotificationServiceImpl.class);

    @Value("${cred.notifications.push-notification.twilio.api-key}")
    private String twilioApiKey;

    @Value("${cred.notifications.push-notification.twilio.api-secret}")
    private String twilioApiSecret;

    @Value("${cred.notifications.push-notification.twilio.account-sid}")
    private String twilioAccountSid;

    @Value("${cred.notifications.push-notification.twilio.notification-service-sid}")
    private String twilioNotificationServiceSid;

    private final TwilioUtil twilioUtil;

    @Autowired
    public TwilioPushNotificationServiceImpl(TwilioUtil twilioUtil) {
        this.twilioUtil = twilioUtil;
    }

    @Override
    public void register(String identity, String address, BindingType bindingType) throws PushNotificationException {

        logger.info("Identity = " + identity + ", address = " + address + ", bindingType = " + bindingType.name());

        try {
            // Authenticate with Twilio
            Twilio.init(twilioApiKey, twilioApiSecret, twilioAccountSid);

            Binding binding = twilioUtil.createBinding(twilioNotificationServiceSid, identity, bindingType, address);

            logger.info("Binding successfully created");
            logger.info(binding.toString());

        } catch (Exception ex) {
            logAndThrow("Exception creating binding: " + ex.getMessage());
        }
    }

    @Override
    public void sendNotification(String identity, String notificationText, String priority) {

        logger.info("identity = " + identity + ", notificationText = " + notificationText);

        try {
            // Authenticate with Twilio
            Twilio.init(twilioApiKey, twilioApiSecret, twilioAccountSid);

            Notification notification = twilioUtil.createNotification(twilioNotificationServiceSid, identity, notificationText, priority);

            logger.info("Notification successfully created");
            logger.info(notification.toString());

        } catch (Exception ex) {
            logAndThrow("Exception creating notification: " + ex.getMessage());
        }
    }

    private void logAndThrow(String message) {
        logger.warn(message);
        throw new PushNotificationException(message);
    }
}
