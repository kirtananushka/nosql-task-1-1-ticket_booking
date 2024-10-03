package com.tananushka.ticketbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccountDTO {
   private String id;
  private BigDecimal balance;
}
