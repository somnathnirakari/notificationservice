package com.cred.notification.services.impl;

import com.authy.AuthyApiClient;
import com.authy.api.Params;
import com.authy.api.Verification;
import com.cred.notification.exceptions.NotificationError;
import com.cred.notification.exceptions.TokenVerificationException;
import com.cred.notification.payloads.DeliveryMode;
import com.cred.notification.services.PhoneVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TwilioPhoneVerificationServiceImpl implements PhoneVerificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioPhoneVerificationServiceImpl.class);

    private final AuthyApiClient authyApiClient;

    private final MessageSource messageSource;

    @Value("${cred.notifications.phone-verification.twilio.code-length}")
    private String codeLength;

    @Autowired
    public TwilioPhoneVerificationServiceImpl(AuthyApiClient authyApiClient, MessageSource messageSource) {
        this.authyApiClient = authyApiClient;
        this.messageSource = messageSource;
    }

    public void start(String countryCode, String phoneNumber, DeliveryMode via, Locale locale) {
        Params params = new Params();
        params.setAttribute("code_length", codeLength);
        params.setAttribute("locale", locale.toString());
        Verification verification = authyApiClient
                .getPhoneVerification()
                .start(phoneNumber, countryCode, via.name(), params);

        if(!verification.isOk()) {
            logAndThrow("Error requesting phone verification. " +
                    getLocalizedMessage(verification.getMessage(), locale));
        }
    }

    public void verify(String countryCode, String phoneNumber, String token, Locale locale) {
        Verification verification = authyApiClient
                .getPhoneVerification()
                .check(phoneNumber, countryCode, token);

        if(!verification.isOk()) {
            logAndThrow("Error verifying token. " +
                    getLocalizedMessage(verification.getMessage(), locale));
        }
    }

    private void logAndThrow(String message) {
        LOGGER.warn(message);
        throw new TokenVerificationException(message);
    }

    private String getLocalizedMessage(String message, Locale locale) {
        return messageSource.getMessage(NotificationError.getNotificationErrorFromMessage(message).name(), null, locale);
    }
}

