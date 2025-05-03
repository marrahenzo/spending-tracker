package io.github.marrahenzo.spending_tracker.dto;

import io.github.marrahenzo.spending_tracker.model.Category;
import io.github.marrahenzo.spending_tracker.model.Operation;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class OperationResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private String currency;
    private Category category;
    private LocalDateTime date;
    private String user;

    public static OperationResponse fromOperation(Operation operation) {
        return OperationResponse.builder()
                .id(operation.getId())
                .description(operation.getDescription())
                .amount(operation.getAmount())
                .currency(operation.getCurrency().name())
                .category(operation.getCategory())
                .date(operation.getDate())
                .user(operation.getUser().getName())
                .build();
    }
}
