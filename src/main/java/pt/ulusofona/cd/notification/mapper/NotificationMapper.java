package pt.ulusofona.cd.notification.mapper;

import org.springframework.stereotype.Component;
import pt.ulusofona.cd.notification.dto.NotificationRequest;
import pt.ulusofona.cd.notification.dto.NotificationResponse;
import pt.ulusofona.cd.notification.model.Notification;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequest request) {
        return Notification.builder()
                .reservationId(request.getReservationId())
                .eventType(request.getEventType())
                .recipient(request.getRecipient())
                .status(request.getStatus() != null ? request.getStatus() : "SENT")
                .build();
    }

    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .reservationId(notification.getReservationId())
                .eventType(notification.getEventType())
                .recipient(notification.getRecipient())
                .status(notification.getStatus())
                .sentAt(notification.getSentAt())
                .build();
    }
}