package io.github.marrahenzo.spending_tracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String message;
    private String errorCode;
    private String exception;
}
