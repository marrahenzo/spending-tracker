package io.github.marrahenzo.spending_tracker.dto;

import io.github.marrahenzo.spending_tracker.model.Category;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private String user;

    public static CategoryResponse fromCategory(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .user(category.getUser().getName())
                .build();
    }
}
