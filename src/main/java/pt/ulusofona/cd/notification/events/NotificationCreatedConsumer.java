package pt.ulusofona.cd.notification.events;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import pt.ulusofona.cd.notification.dto.MessageEnvelope;
import pt.ulusofona.cd.notification.dto.NotificationRequest;
import pt.ulusofona.cd.notification.dto.ReservationCreatedEvent;
import pt.ulusofona.cd.notification.service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationCreatedConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltTopicSuffix = "-dlt"
    )
    @KafkaListener(topics = "${notification.events.reservation-created}", groupId = "notification-group")
    public void consume(String rawMessage) {
        try {
            MessageEnvelope<ReservationCreatedEvent> envelope = objectMapper.readValue(
                    rawMessage, new TypeReference<>() {}
            );
            ReservationCreatedEvent event = envelope.getPayload();

            log.info("Consumer [Created] received event for reservation: {}", event.getReservationId());

            notificationService.createNotification(NotificationRequest.builder()
                    .reservationId(event.getReservationId())
                    .eventType("CREATED_PENDING")
                    .recipient(event.getCustomerEmail())
                    .status("SENT")
                    .build());

        } catch (Exception e) {
            log.error("Error processing Reservation Created Event", e);
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void handleDlt(String rawMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("Message moved to DLT topic {}. Payload: {}", topic, rawMessage);
    }
}