package pt.ulusofona.cd.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.notification.dto.NotificationRequest;
import pt.ulusofona.cd.notification.dto.NotificationResponse;
import pt.ulusofona.cd.notification.service.NotificationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest request) {
        NotificationResponse created = notificationService.createNotification(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody NotificationRequest request) {
        NotificationResponse updated = notificationService.updateNotification(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}