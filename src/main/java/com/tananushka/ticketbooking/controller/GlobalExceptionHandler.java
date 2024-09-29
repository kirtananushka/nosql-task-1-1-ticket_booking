package com.tananushka.ticketbooking.controller;

import com.tananushka.ticketbooking.exception.TicketBookingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(TicketBookingException.class)
  public ResponseEntity<Map<String, Object>> handleTicketBooking(HttpServletRequest request, TicketBookingException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("path", request.getRequestURI());
    errorResponse.put("status", ex.getErrorCode());
    return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(HttpServletRequest request, Exception ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("path", request.getRequestURI());
    errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
