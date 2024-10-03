package com.tananushka.ticketbooking.mapper;

import com.tananushka.ticketbooking.dto.UserAccountDTO;
import com.tananushka.ticketbooking.model.mongo.MongoUserAccount;
import com.tananushka.ticketbooking.model.postgres.UserAccount;

public class UserAccountMapper {

  private UserAccountMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static UserAccountDTO toDto(UserAccount userAccount) {
    if (userAccount == null) return null;
    UserAccountDTO userAccountDTO = new UserAccountDTO();
     userAccountDTO.setId(String.valueOf(userAccount.getId()));
    userAccountDTO.setBalance(userAccount.getBalance());
    return userAccountDTO;
  }

  public static UserAccount toEntity(UserAccountDTO userAccountDTO) {
    if (userAccountDTO == null) return null;
    UserAccount userAccount = new UserAccount();
     userAccount.setId(Long.valueOf(userAccountDTO.getId()));
     userAccount.setBalance(userAccountDTO.getBalance());
     return userAccount;
  }

   public static UserAccountDTO toDto(MongoUserAccount userAccount) {
      if (userAccount == null) return null;
      UserAccountDTO userAccountDTO = new UserAccountDTO();
      userAccountDTO.setBalance(userAccount.getBalance());
      return userAccountDTO;
   }

   public static MongoUserAccount toMongoEntity(UserAccountDTO userAccountDTO) {
      if (userAccountDTO == null) return null;
      MongoUserAccount userAccount = new MongoUserAccount();
    userAccount.setBalance(userAccountDTO.getBalance());
    return userAccount;
  }
}
