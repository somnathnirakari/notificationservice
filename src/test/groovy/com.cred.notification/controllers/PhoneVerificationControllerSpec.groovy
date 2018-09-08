package com.cred.notification.controllers

import com.cred.notification.exceptions.PhoneVerificationException
import com.cred.notification.payloads.DeliveryMode
import com.cred.notification.payloads.PhoneVerificationStartRequest
import com.cred.notification.payloads.PhoneVerificationVerifyRequest
import com.cred.notification.services.PhoneVerificationService
import groovy.json.JsonBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

class PhoneVerificationControllerSpec extends Specification {

    def phoneVerificationService = Mock(PhoneVerificationService)
    @Subject
    def phoneVerificationController = new PhoneVerificationController(phoneVerificationService)

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(phoneVerificationController).build()

    static phone = '1'
    static countryCode = '2'
    static via = DeliveryMode.SMS
    static token = 'token'
    static locale = Locale.US

    def "start - returns 200"() {
        given:
        def request = new PhoneVerificationStartRequest(phone, countryCode, via)
        def requestBody = new JsonBuilder(request).toString()
        1 * phoneVerificationService.start(countryCode, phone, via, locale)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post('/api/notifications/phone-verification/start')
                .content(requestBody)
                .locale(locale)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().response

        then:
        response.status == 200
    }

    def "start - returns 500 for PhoneVerificationException"() {
        given:
        def request = new PhoneVerificationStartRequest(phone, countryCode, via)
        def requestBody = new JsonBuilder(request).toString()
        1 * phoneVerificationService.start(countryCode, phone, via, locale) >> {
            throw new PhoneVerificationException('message')
        }

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post('/api/notifications/phone-verification/start')
                .content(requestBody)
                .locale(locale)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().response

        then:
        response.status == 500
        response.contentAsString == 'message'
    }

    def "verify - returns 200"() {
        given:
        def httpRequest = new PhoneVerificationVerifyRequest(phone, countryCode, token)
        def requestBody = new JsonBuilder(httpRequest).toString()
        1 * phoneVerificationService.verify(countryCode, phone, token, locale)

        expect:
        mockMvc
            .perform(MockMvcRequestBuilders.post('/api/notifications/phone-verification/verify')
                    .content(requestBody)
                    .locale(locale)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "verify - returns 500 for PhoneVerificationException"() {
        given:
        def httpRequest = new PhoneVerificationVerifyRequest(phone, countryCode, token)
        def requestBody = new JsonBuilder(httpRequest).toString()
        1 * phoneVerificationService.verify(countryCode, phone, token, locale) >> {
            throw new PhoneVerificationException('message')
        }

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post('/api/notifications/phone-verification/verify')
                .content(requestBody)
                .locale(locale)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().response

        then:
        response.status == 500
        response.contentAsString == 'message'
    }
}
