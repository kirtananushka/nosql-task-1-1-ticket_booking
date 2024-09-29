package com.tananushka.ticketbooking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefillRequestDTO {
  private BigDecimal amount;
}
