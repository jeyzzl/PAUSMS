package aca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToAdmin(String message, String recipientId) {
        // Long adminUserId = 1L; // Replace with the actual admin user ID
        System.out.println(recipientId);
        Notification notification = new Notification(message);
        messagingTemplate.convertAndSendToUser(
                recipientId, // Destination user (admin)
                "queue/notifications", // Destination queue
                notification // Notification payload
        );
    }
}
