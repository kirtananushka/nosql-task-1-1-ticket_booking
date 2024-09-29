package com.tananushka.ticketbooking.mapper;

import com.tananushka.ticketbooking.dto.UserAccountDTO;
import com.tananushka.ticketbooking.model.UserAccount;

public class UserAccountMapper {

  private UserAccountMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static UserAccountDTO toDto(UserAccount userAccount) {
    if (userAccount == null) return null;

    UserAccountDTO userAccountDTO = new UserAccountDTO();
    userAccountDTO.setId(userAccount.getId());
    userAccountDTO.setBalance(userAccount.getBalance());

    return userAccountDTO;
  }

  public static UserAccount toEntity(UserAccountDTO userAccountDTO) {
    if (userAccountDTO == null) return null;

    UserAccount userAccount = new UserAccount();
    userAccount.setId(userAccountDTO.getId());
    userAccount.setBalance(userAccountDTO.getBalance());

    return userAccount;
  }
}
