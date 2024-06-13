package org.example.orderservice.Exceptions;

public class AuthFailedException extends RuntimeException {
    public AuthFailedException(String message) {
        super(message);
    }
}
