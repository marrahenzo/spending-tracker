package io.github.marrahenzo.spending_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    US_DOLLARS("U.S. Dollars", "USD", "US$"),
    ARG_PESOS("Argentine Pesos", "ARS", "$");

    private String displayName;
    private String abbreviation;
    private String symbol;
}
