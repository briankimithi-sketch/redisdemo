package com.abcbank.redis2.model;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginEvent {

    private String username;
    private LocalDateTime timestamp;
    private UUID eventId;
    public LoginEvent() {
        this.eventId = UUID.randomUUID();
    }

    public LoginEvent(String username, LocalDateTime timestamp) {
        this(username, timestamp, UUID.randomUUID());
    }
}
