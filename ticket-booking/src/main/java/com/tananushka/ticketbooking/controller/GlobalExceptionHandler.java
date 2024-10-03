package com.tananushka.ticketbooking.controller;

import com.tananushka.ticketbooking.exception.TicketBookingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

   @ExceptionHandler(TicketBookingException.class)
   public ResponseEntity<Map<String, Object>> handleTicketBooking(HttpServletRequest request, TicketBookingException ex) {
      Map<String, Object> errorResponse = createErrorResponse(request, ex);
      return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode()));
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<Map<String, Object>> handleException(HttpServletRequest request, Exception ex) {
      Map<String, Object> errorResponse = createErrorResponse(request, ex, "500");
      return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   private Map<String, Object> createErrorResponse(HttpServletRequest request, TicketBookingException ex) {
      return createErrorResponse(request, ex, String.valueOf(ex.getErrorCode()));
   }

   private Map<String, Object> createErrorResponse(HttpServletRequest request, Exception ex, String errorCode) {
      log.error("Error occurred: ", ex);
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("message", ex.getMessage());
      errorResponse.put("path", request.getRequestURI());
      errorResponse.put("status", errorCode);
      return errorResponse;
   }
}
