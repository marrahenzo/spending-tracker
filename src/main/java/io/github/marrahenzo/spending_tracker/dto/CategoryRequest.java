package io.github.marrahenzo.spending_tracker.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryRequest {

    private String name;
}
