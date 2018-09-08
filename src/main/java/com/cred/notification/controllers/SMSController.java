package com.cred.notification.controllers;

import com.cred.notification.payloads.SendSMSRequest;
import com.cred.notification.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/notifications/sms")
public class SMSController extends BaseController{

    private SMSService smsService;

    @Autowired
    public SMSController(SMSService smsService) {
        this.smsService = smsService;
    }

    @RequestMapping(path = "send", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity send(@Valid @RequestBody SendSMSRequest requestBody) {
        return runWithCatch(() -> {
            smsService.send(requestBody);
        });
    }

}
