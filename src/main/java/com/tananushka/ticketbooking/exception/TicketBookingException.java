package com.tananushka.ticketbooking.exception;

import lombok.Getter;

@Getter
public class TicketBookingException extends RuntimeException {
  private final int errorCode;

  public TicketBookingException(String message) {
    super(message);
    this.errorCode = 500;
  }

  public TicketBookingException(String message, int errorCode) {
    super(message);
    this.errorCode = errorCode;
  }
}
