package com.cred.notification.services


import com.cred.notification.exceptions.PushNotificationException
import com.cred.notification.payloads.BindingType
import com.cred.notification.services.impl.TwilioPushNotificationServiceImpl
import com.cred.notification.util.TwilioUtil
import com.twilio.exception.ApiException
import com.twilio.rest.notify.v1.service.Binding
import com.twilio.rest.notify.v1.service.Notification
import org.springframework.util.ReflectionUtils
import spock.lang.Specification
import spock.lang.Subject

import java.lang.reflect.Field

class PushNotificationServiceSpec extends Specification {

    TwilioUtil twilioUtil = Mock()
    Binding binding = Mock()
    Notification notification = Mock()

    @Subject PushNotificationService pushNotificationService

    String identity = "test_user"
    String address = "rwefvsvdcwef123423423gdsfvdvcvdss"
    String notificationText = "Sample text"

    private String twilioNotificationServiceSid = "testNotificationServiceSid"

    def setup() {
        pushNotificationService = new TwilioPushNotificationServiceImpl(twilioUtil)
        Field fieldTwilioApiKey = ReflectionUtils.findField(TwilioPushNotificationServiceImpl.class, "twilioApiKey")
        ReflectionUtils.makeAccessible(fieldTwilioApiKey);
        ReflectionUtils.setField(fieldTwilioApiKey, pushNotificationService, "testApiKey")

        Field fieldTwilioApiSecret = ReflectionUtils.findField(TwilioPushNotificationServiceImpl.class, "twilioApiSecret")
        ReflectionUtils.makeAccessible(fieldTwilioApiSecret);
        ReflectionUtils.setField(fieldTwilioApiSecret, pushNotificationService, "testApiSecret")

        Field fieldTwilioAccountSid = ReflectionUtils.findField(TwilioPushNotificationServiceImpl.class, "twilioAccountSid")
        ReflectionUtils.makeAccessible(fieldTwilioAccountSid);
        ReflectionUtils.setField(fieldTwilioAccountSid, pushNotificationService, "testAccountSid")

        Field fieldTwilioNotificationServiceSid = ReflectionUtils.findField(TwilioPushNotificationServiceImpl.class, "twilioNotificationServiceSid")
        ReflectionUtils.makeAccessible(fieldTwilioNotificationServiceSid);
        ReflectionUtils.setField(fieldTwilioNotificationServiceSid, pushNotificationService, "testNotificationServiceSid")

    }

    def "register - success"() {
        given:
        1 * twilioUtil.createBinding(twilioNotificationServiceSid, identity, BindingType.APN, address) >> binding
        1 * binding.toString() >> "Binding object"
        when:
        pushNotificationService.register(identity, address, BindingType.APN)

        then:
        notThrown PushNotificationException
    }

    def "register - error"() {
        given:
        1 * twilioUtil.createBinding(twilioNotificationServiceSid, identity, BindingType.APN, address) >>
                { throw new ApiException("Binding failed") }
        0 * binding.toString() >> "Binding object"

        when:
        pushNotificationService.register(identity, address, BindingType.APN)

        then:
        PushNotificationException e = thrown()
        e.getMessage() == 'Exception creating binding: Binding failed'
    }

    def "send notification - success"() {
        given:
        1 * twilioUtil.createNotification(twilioNotificationServiceSid, identity, notificationText, null) >> notification
        1 * notification.toString() >> "notification object"
        when:
        pushNotificationService.sendNotification(identity, "Sample text", null)

        then:
        notThrown PushNotificationException
    }

    def "send notification - error"() {
        given:
        1 * twilioUtil.createNotification(twilioNotificationServiceSid, identity, notificationText, null) >>
                { throw new ApiException("Notification failed") }
        0 * notification.toString()

        when:
        pushNotificationService.sendNotification(identity, notificationText, null)

        then:
        PushNotificationException e = thrown()
        e.getMessage() == 'Exception creating notification: Notification failed'
    }

}
