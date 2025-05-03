package io.github.marrahenzo.spending_tracker.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OperationRequest {

    private Long id;
    private String description;
    private String amount;
    private String currency;
    private Long category;
    private String date;
}
