package com.cred.notification.controllers;

import com.cred.notification.payloads.RegisterDeviceRequest;
import com.cred.notification.payloads.SendNotificationRequest;
import com.cred.notification.services.PhoneVerificationService;
import com.cred.notification.services.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/notifications/push-notification")
public class PushNotificationController extends BaseController {

    private PushNotificationService pushNotificationService;

    @Autowired
    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @RequestMapping(path = "register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegisterDeviceRequest registerDeviceRequest) {
        return runWithCatch(()-> {
            pushNotificationService.register(registerDeviceRequest.getIdentity(),
                                                            registerDeviceRequest.getAddress(),
                                                            registerDeviceRequest.getBindingType());
        });
    }

    @RequestMapping(path = "send-notification", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity sendNotification(@Valid @RequestBody SendNotificationRequest sendNotificationRequest) {
        return runWithCatch(()-> {
            pushNotificationService.sendNotification(sendNotificationRequest.getIdentity(),
                    sendNotificationRequest.getBody(),
                    sendNotificationRequest.getPriority());
        });
    }
}
