package org.bookrental.exception;

public class InvalidRentalOperationException extends RuntimeException {
    public InvalidRentalOperationException(String message) {
        super(message);
    }
}
