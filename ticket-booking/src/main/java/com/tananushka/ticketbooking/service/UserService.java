package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.UserDTO;

import java.util.List;

public interface UserService {

   UserDTO getUserById(String userId);

   List<UserDTO> getAllUsers();

   UserDTO createUser(UserDTO userDTO);

   UserDTO updateUser(String id, UserDTO userDTO);

   boolean deleteUser(String userId);
}