package com.abcbank.redis2.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class LoginEvent {

    private String username;
    private LocalDateTime timestamp;
    private UUID eventId;

    public LoginEvent() {
        this.eventId = UUID.randomUUID();
    }

    public LoginEvent(String username, LocalDateTime timestamp) {
        this.username = username;
        this.timestamp = timestamp;
        this.eventId = UUID.randomUUID();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginEvent)) return false;
        LoginEvent that = (LoginEvent) o;
        return Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public String toString() {
        return "LoginEvent{" +
                "username='" + username + '\'' +
                ", timestamp=" + timestamp +
                ", eventId=" + eventId +
                '}';
    }
}
