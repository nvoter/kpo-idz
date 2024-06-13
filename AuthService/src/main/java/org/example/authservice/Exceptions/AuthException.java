package org.example.authservice.Exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Setter
@Getter
public class AuthException extends AuthenticationException {
    public HttpStatus httpStatus;

    public AuthException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}