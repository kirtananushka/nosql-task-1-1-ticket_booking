package com.tananushka.ticketbooking.dto;

import lombok.Data;

@Data
public class UserDTO {
   private String id;
  private String firstName;
  private String lastName;
  private String email;
  private UserAccountDTO userAccount;
}
