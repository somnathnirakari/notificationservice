package com.cred.notification.controllers;

import com.cred.notification.payloads.PhoneVerificationStartRequest;
import com.cred.notification.payloads.PhoneVerificationVerifyRequest;
import com.cred.notification.services.PhoneVerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@Api(description = "end points for sending phone verification codes and verifying them")
@RestController
@RequestMapping(path = "/api/notifications/phone-verification")
public class PhoneVerificationController extends BaseController {

    private PhoneVerificationService phoneVerificationService;

    @Autowired
    public PhoneVerificationController(PhoneVerificationService phoneVerificationService) {
        this.phoneVerificationService = phoneVerificationService;
    }

    @ApiOperation("starts a phone verification process and sends a code to the provided phone number")
    @RequestMapping(path = "start", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity start(@ApiParam("Requested locale") @RequestHeader("Accept-Language") Locale locale,
                                @ApiParam("Phone verification start request") @Valid @RequestBody PhoneVerificationStartRequest requestBody) {
        return runWithCatch(() -> {
            phoneVerificationService.start(requestBody.getCountryCode(),
                    requestBody.getPhoneNumber(),
                    requestBody.getVia(),
                    locale);
        });
    }

    @ApiOperation("verifies the code sent to the phone number is valid")
    @RequestMapping(path = "verify", method = RequestMethod.POST)
    public ResponseEntity verify(@ApiParam("Requested locale") @RequestHeader("Accept-Language") Locale locale,
                                 @ApiParam("Phone verification verify request") @Valid @RequestBody PhoneVerificationVerifyRequest requestBody) {
        return runWithCatch(() -> {
            phoneVerificationService.verify(requestBody.getCountryCode(),
                    requestBody.getPhoneNumber(),
                    requestBody.getToken(),
                    locale);
        });
    }
}

