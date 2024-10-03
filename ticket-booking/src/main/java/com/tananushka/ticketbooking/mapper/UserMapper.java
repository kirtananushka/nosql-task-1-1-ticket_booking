package com.tananushka.ticketbooking.mapper;

import com.tananushka.ticketbooking.dto.UserDTO;
import com.tananushka.ticketbooking.model.mongo.MongoUserEntity;
import com.tananushka.ticketbooking.model.postgres.UserEntity;

public class UserMapper {
  private UserMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static UserDTO toDto(UserEntity userEntity) {
    if (userEntity == null) return null;
    UserDTO userDTO = new UserDTO();
     userDTO.setId(String.valueOf(userEntity.getId()));
    userDTO.setFirstName(userEntity.getFirstName());
    userDTO.setLastName(userEntity.getLastName());
    userDTO.setEmail(userEntity.getEmail());
    userDTO.setUserAccount(UserAccountMapper.toDto(userEntity.getUserAccount()));
    return userDTO;
  }

  public static UserEntity toEntity(UserDTO userDTO) {
    if (userDTO == null) return null;
    UserEntity userEntity = new UserEntity();
     userEntity.setId(userDTO.getId() != null ? Long.valueOf(userDTO.getId()) : null);
     userEntity.setFirstName(userDTO.getFirstName());
     userEntity.setLastName(userDTO.getLastName());
     userEntity.setEmail(userDTO.getEmail());
     userEntity.setUserAccount(UserAccountMapper.toEntity(userDTO.getUserAccount()));
     return userEntity;
  }

   public static UserDTO toDto(MongoUserEntity userEntity) {
      if (userEntity == null) return null;
      UserDTO userDTO = new UserDTO();
      userDTO.setId(userEntity.getId());
      userDTO.setFirstName(userEntity.getFirstName());
      userDTO.setLastName(userEntity.getLastName());
      userDTO.setEmail(userEntity.getEmail());
      userDTO.setUserAccount(UserAccountMapper.toDto(userEntity.getUserAccount()));
      return userDTO;
   }

   public static MongoUserEntity toMongoEntity(UserDTO userDTO) {
      if (userDTO == null) return null;
      MongoUserEntity userEntity = new MongoUserEntity();
    userEntity.setId(userDTO.getId());
    userEntity.setFirstName(userDTO.getFirstName());
    userEntity.setLastName(userDTO.getLastName());
    userEntity.setEmail(userDTO.getEmail());
      userEntity.setUserAccount(UserAccountMapper.toMongoEntity(userDTO.getUserAccount()));
    return userEntity;
  }

}
