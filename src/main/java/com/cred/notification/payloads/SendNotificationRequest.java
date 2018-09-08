package com.cred.notification.payloads;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendNotificationRequest {

    @NotNull
    String identity;
    @NotNull
    String body;
    String priority;

    public SendNotificationRequest(@NotNull String identity, @NotNull String body, String priority) {
        this.identity = identity;
        this.body = body;
        this.priority = priority;
    }
}
