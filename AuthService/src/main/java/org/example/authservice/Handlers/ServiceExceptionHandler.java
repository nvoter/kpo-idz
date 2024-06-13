//package org.example.authservice.Handlers;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import org.example.authservice.Exceptions.AuthException;
//import org.example.authservice.Exceptions.UserNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import javax.servlet.http.HttpServletResponse;
//
//@RestControllerAdvice
//public class ServiceExceptionHandler {
//    @ExceptionHandler({AuthException.class, BadCredentialsException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleAuthException(Exception e, HttpServletResponse response) {
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        return new ErrorResponse("Authentication error: " + e.getMessage());
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleUserNotFoundException(UserNotFoundException e, HttpServletResponse response) {
//        response.setStatus(HttpStatus.NOT_FOUND.value());
//        return new ErrorResponse("User not found: " + e.getMessage());
//    }
//
//    @Setter
//    @Getter
//    @AllArgsConstructor
//    private static class ErrorResponse {
//        private String message;
//    }
//}

package org.example.authservice.Handlers;

import org.example.authservice.Exceptions.AuthException;
import org.example.authservice.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler({AuthException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return "Authentication error: " + e.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "User not found: " + e.getMessage();
    }
}