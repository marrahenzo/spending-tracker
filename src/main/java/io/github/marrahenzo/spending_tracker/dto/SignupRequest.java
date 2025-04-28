package io.github.marrahenzo.spending_tracker.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String name;
}
