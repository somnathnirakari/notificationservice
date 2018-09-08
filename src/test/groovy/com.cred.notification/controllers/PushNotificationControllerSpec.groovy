package com.cred.notification.controllers


import com.cred.notification.exceptions.PushNotificationException
import com.cred.notification.payloads.BindingType
import com.cred.notification.payloads.RegisterDeviceRequest
import com.cred.notification.payloads.SendNotificationRequest
import com.cred.notification.services.PushNotificationService
import groovy.json.JsonBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

class PushNotificationControllerSpec extends Specification {

    def pushNotificationService = Mock(PushNotificationService)

    @Subject
    def pushNotificationController = new PushNotificationController(pushNotificationService)

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(pushNotificationController).build()

    static identity = 'test_user'
    static address = 'asfsdf87987sddsgsdsdf'
    static bindingType = BindingType.APN
    static notificationText = "Some message"

    def "register - returns 200"() {
        given:
        def request = new RegisterDeviceRequest(identity, address, bindingType)
        def requestBody = new JsonBuilder(request).toString()
        1 * pushNotificationService.register(identity, address, bindingType)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post('/api/notifications/push-notification/register')
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().response

        then:
        response.status == 200
    }

    def "register - returns 500 for PushNotificationException"() {
        given:
        def request = new RegisterDeviceRequest(identity, address, bindingType)
        def requestBody = new JsonBuilder(request).toString()
        1 * pushNotificationService.register(identity, address, bindingType) >> {
            throw new PushNotificationException('message')
        }

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post('/api/notifications/push-notification/register')
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().response

        then:
        response.status == 500
        response.contentAsString == 'message'
    }

    def "send-notification - returns 200"() {
        given:
        def httpRequest = new SendNotificationRequest(identity, notificationText, null)
        def requestBody = new JsonBuilder(httpRequest).toString()
        1 * pushNotificationService.sendNotification(identity, notificationText, null)

        expect:
        mockMvc
            .perform(MockMvcRequestBuilders.post('/api/notifications/push-notification/send-notification')
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "send-notification - returns 500 for PushNotificationException"() {
        given:
        def httpRequest = new SendNotificationRequest(identity, notificationText, null)
        def requestBody = new JsonBuilder(httpRequest).toString()
        1 * pushNotificationService.sendNotification(identity, notificationText, null) >> {
            throw new PushNotificationException('message')
        }

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post('/api/notifications/push-notification/send-notification')
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().response

        then:
        response.status == 500
        response.contentAsString == 'message'
    }
}
