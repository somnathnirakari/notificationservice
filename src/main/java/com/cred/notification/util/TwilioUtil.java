package com.cred.notification.util;

import com.cred.notification.payloads.BindingType;
import com.twilio.rest.notify.v1.service.Binding;
import com.twilio.rest.notify.v1.service.BindingCreator;
import com.twilio.rest.notify.v1.service.Notification;
import com.twilio.rest.notify.v1.service.NotificationCreator;
import org.springframework.stereotype.Component;

@Component
public class TwilioUtil {

    public Binding createBinding(String twilioNotificationServiceSid, String identity, BindingType bindingType, String address) {
        BindingCreator bindingCreator = new BindingCreator(twilioNotificationServiceSid, identity, Binding.BindingType.valueOf(bindingType.name()), address);
        return bindingCreator.create();
    }

    public Notification createNotification(String twilioNotificationServiceSid, String identity, String notificationText, String priority) {
        NotificationCreator notificationCreator = new NotificationCreator(twilioNotificationServiceSid);
        notificationCreator.setIdentity(identity);
        notificationCreator.setBody(notificationText);

        if (priority!=null) {
            // Convert Priority from Object to enum value
            notificationCreator.setPriority(Notification.Priority.forValue(priority));
        }
        return notificationCreator.create();
    }
}
