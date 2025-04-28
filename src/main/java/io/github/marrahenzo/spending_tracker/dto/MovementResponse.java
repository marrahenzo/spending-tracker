package io.github.marrahenzo.spending_tracker.dto;

import io.github.marrahenzo.spending_tracker.model.Category;
import io.github.marrahenzo.spending_tracker.model.Currency;
import io.github.marrahenzo.spending_tracker.model.Movement;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class MovementResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private Currency currency;
    private Category category;
    private LocalDateTime date;
    private String user;

    public static MovementResponse fromMovement(Movement movement) {
        return MovementResponse.builder()
                .id(movement.getId())
                .description(movement.getDescription())
                .amount(movement.getAmount())
                .currency(movement.getCurrency())
                .category(movement.getCategory())
                .date(movement.getDate())
                .user(movement.getUser().getName())
                .build();
    }
}
