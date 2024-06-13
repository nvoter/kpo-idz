package org.example.orderservice.Handlers;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.example.orderservice.Exceptions.AuthFailedException;
import org.example.orderservice.Exceptions.StationNotFoundException;
import org.example.orderservice.Exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Hidden
public class OrderExceptionHandler {
    @ExceptionHandler(AuthFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthFailedException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ErrorResponse("Auth failed: " + e.getMessage());
    }

    @ExceptionHandler(RestClientException.class)
   public ErrorResponse handleRestClientExceptgion(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ErrorResponse("Invalid token");
    }

    @ExceptionHandler({OrderNotFoundException.class, StationNotFoundException.class})
    @ResponseBody
    public ErrorResponse handleNotFoundException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        if (e.getClass() == OrderNotFoundException.class)
            return new ErrorResponse("Order not found");
        else return new ErrorResponse("Station not found");
    }

    @Setter
    @Getter
    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}
