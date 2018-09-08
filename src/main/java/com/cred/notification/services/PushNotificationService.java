package com.cred.notification.services;

import com.cred.notification.exceptions.PushNotificationException;
import com.cred.notification.payloads.BindingType;

public interface PushNotificationService {

    void register(String identity, String address, BindingType bindingType) throws PushNotificationException;

    void sendNotification(String identity, String notificationText, String priority);
}
