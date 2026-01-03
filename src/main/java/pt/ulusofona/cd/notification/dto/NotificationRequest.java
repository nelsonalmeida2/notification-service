package pt.ulusofona.cd.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotNull(message = "Reservation ID is required")
    private UUID reservationId;

    @NotBlank(message = "Event type is required")
    private String eventType;

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    private String recipient;

    private String status;
}