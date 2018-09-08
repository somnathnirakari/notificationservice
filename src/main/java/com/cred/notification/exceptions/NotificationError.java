package com.cred.notification.exceptions;

import java.util.HashMap;
import java.util.Map;

public enum NotificationError {
    CNE_0(0, "Undefined"),
    CNE_100(1,"Phone number is invalid");

    private final int code;
    private final String description;

    private NotificationError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

    private static final Map<String, NotificationError> lookup = new HashMap<>();

    static {
        for (NotificationError error: NotificationError.values()) {
            lookup.put(error.description, error);
        }
    }

    public static NotificationError getNotificationErrorFromMessage(String message) {
        return lookup.getOrDefault(message, NotificationError.CNE_0);
    }
}
