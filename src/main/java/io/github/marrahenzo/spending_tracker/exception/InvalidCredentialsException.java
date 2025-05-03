package io.github.marrahenzo.spending_tracker.exception;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("The provided credentials are invalid");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
