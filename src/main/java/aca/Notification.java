package aca;

import java.time.LocalDateTime;

public class Notification {
    private String message;
    private LocalDateTime timestamp;

    // Default constructor (required for JSON serialization/deserialization)
    public Notification() {}

    // Parameterized constructor
    public Notification(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
