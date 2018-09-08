package com.cred.notification.services

import com.authy.AuthyApiClient
import com.authy.api.Params
import com.authy.api.PhoneVerification
import com.authy.api.Verification
import com.cred.notification.exceptions.NotificationError
import com.cred.notification.exceptions.TokenVerificationException
import com.cred.notification.payloads.DeliveryMode
import com.cred.notification.services.PhoneVerificationService
import com.cred.notification.services.impl.TwilioPhoneVerificationServiceImpl
import org.springframework.context.MessageSource
import spock.lang.Specification
import spock.lang.Subject

class PhoneVerificationServiceSpec extends Specification {

    AuthyApiClient authyApiClient = Mock()
    MessageSource messageSource = Mock()
    PhoneVerification phoneVerification = Mock()

    @Subject PhoneVerificationService phoneVerificationService

    static phone = '1'
    static countryCode = '2'
    static via = DeliveryMode.SMS
    static token = 'token'
    static locale = Locale.US

    def setup() {
        phoneVerificationService = new TwilioPhoneVerificationServiceImpl(authyApiClient, messageSource)
        1 * authyApiClient.getPhoneVerification() >> phoneVerification
    }

    def "start - success"() {
        given:
        1 * phoneVerification.start(phone, countryCode, via.name(), _ as Params) >>
                new Verification(200, '', '')

        when:
        phoneVerificationService.start(countryCode, phone, via, locale)

        then:
        notThrown TokenVerificationException
    }

    def "start - error"() {
        given:
        Verification verification = new Verification(400, '', 'verification failed')
        1 * phoneVerification.start(phone, countryCode, via.name(), _ as Params) >> verification
        1 * messageSource.getMessage(NotificationError.getNotificationErrorFromMessage(verification.message).name(), null, locale) >> "localized String"

        when:
        phoneVerificationService.start(countryCode, phone, via, locale)

        then:
        TokenVerificationException e = thrown()
        e.getMessage() == 'Error requesting phone verification. ' + "localized String"
    }

    def "verify - success"() {
        given:
        1 * phoneVerification.check(phone, countryCode, token) >>
                new Verification(200, '', '')

        when:
        phoneVerificationService.verify(countryCode, phone, token, locale)

        then:
        notThrown TokenVerificationException
    }

    def "verify - error"() {
        given:
        Verification verification = new Verification(400, '', 'verification failed')

        1 * phoneVerification.check(phone, countryCode, token) >> verification
        1 * messageSource.getMessage(NotificationError.getNotificationErrorFromMessage(verification.message).name(), null, locale) >> "localized String"

        when:
        phoneVerificationService.verify(countryCode, phone, token, locale)

        then:
        TokenVerificationException e = thrown()
        e.getMessage() == 'Error verifying token. ' + "localized String"
    }
}
