package com.tananushka.ticketbooking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventDTO {
   private String id;
  private String title;
  private LocalDateTime date;
  private BigDecimal ticketPrice;
}
