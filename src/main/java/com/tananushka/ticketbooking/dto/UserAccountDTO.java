package com.tananushka.ticketbooking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAccountDTO {
  private Long id;
  private BigDecimal balance;
}
