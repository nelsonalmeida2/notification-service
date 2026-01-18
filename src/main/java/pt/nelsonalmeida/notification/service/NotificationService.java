package pt.nelsonalmeida.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pt.nelsonalmeida.notification.dto.NotificationRequest;
import pt.nelsonalmeida.notification.dto.NotificationResponse;
import pt.nelsonalmeida.notification.mapper.NotificationMapper;
import pt.nelsonalmeida.notification.model.Notification;
import pt.nelsonalmeida.notification.repository.NotificationRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationResponse createNotification(NotificationRequest request) {
        log.info("Processing notification creation for recipient: {}", request.getRecipient());

        sendEmailSimulation(request.getRecipient(),
                String.format("Update on reservation %s: %s", request.getReservationId(), request.getEventType()));

        Notification notification = notificationMapper.toEntity(request);
        Notification savedNotification = notificationRepository.save(notification);

        return notificationMapper.toResponse(savedNotification);
    }

    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse getNotificationById(UUID id) {
        return notificationRepository.findById(id)
                .map(notificationMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));
    }

    public NotificationResponse updateNotification(UUID id, NotificationRequest request) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));

        notification.setReservationId(request.getReservationId());
        notification.setEventType(request.getEventType());
        notification.setRecipient(request.getRecipient());

        if (request.getStatus() != null) {
            notification.setStatus(request.getStatus());
        }

        Notification updatedNotification = notificationRepository.save(notification);
        return notificationMapper.toResponse(updatedNotification);
    }

    public void deleteNotification(UUID id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");
        }
        notificationRepository.deleteById(id);
    }

    private void sendEmailSimulation(String to, String body) {
        log.info("==================================================");
        log.info("📧 MOCK EMAIL SENT");
        log.info("To: {}", to);
        log.info("Body: {}", body);
        log.info("==================================================");
    }
}