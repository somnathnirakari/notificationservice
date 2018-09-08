package com.cred.notification.services.impl;

import com.cred.notification.payloads.SendSMSRequest;
import com.cred.notification.services.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSMSServiceImpl implements SMSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSMSServiceImpl.class);

    @Value("${cred.notifications.sms.twilio.sid}")
    private String messageServiceSid;

    @Value("${cred.notifications.sms.twilio.account-sid}")
    private String accountSid;

    @Value("${cred.notifications.sms.twilio.auth-token}")
    private String authToken;

    @Override
    public void send(SendSMSRequest sendSMSRequest) {

        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(sendSMSRequest.getCountryCode() + sendSMSRequest.getPhoneNumber()),
                messageServiceSid,
                sendSMSRequest.getMessage())
                .create();

        LOGGER.info("Message SID = " + message.getSid());
    }
}
