package com.cred.notification.controllers;

import com.cred.notification.exceptions.PhoneVerificationException;
import com.cred.notification.exceptions.PushNotificationException;
import com.cred.notification.exceptions.TokenVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

abstract class  BaseController {

    protected BaseController() {
    }

    ResponseEntity<? extends Object> runWithCatch(Runnable runnable, HttpStatus status) {
        try {
            runnable.run();
            return ResponseEntity.ok().build();
        } catch (PhoneVerificationException |
                 TokenVerificationException |
                 PushNotificationException e) {
            return ResponseEntity.status(status)
                    .body(e.getMessage());
        }
    }

    ResponseEntity<? extends Object> runWithCatch(Runnable runnable) {
        return this.runWithCatch(runnable, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
