package co.edu.co.spring.demo.mapping.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TransactionsDTO(
        @NotNull(message = "Reservation ID cannot be null")
        Integer reservationId,

        @NotNull(message = "Payment method cannot be null")
        String paymentMethods) {
}
