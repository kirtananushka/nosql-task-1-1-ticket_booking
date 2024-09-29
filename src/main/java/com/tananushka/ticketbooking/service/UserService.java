package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.UserDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.UserMapper;
import com.tananushka.ticketbooking.model.UserEntity;
import com.tananushka.ticketbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserDTO getUserById(Long userId) {
    return userRepository.findById(userId)
            .map(UserMapper::toDto)
            .orElseThrow(() -> new TicketBookingException("User not found", 404));
  }

  public List<UserDTO> getAllUsers() {
    return userRepository.findAll()
            .stream()
            .map(UserMapper::toDto)
            .toList();
  }

  public UserDTO createUser(UserDTO userDTO) {
    if (Boolean.TRUE.equals(userRepository.existsByEmail(userDTO.getEmail()))) {
      throw new TicketBookingException("User with email '" + userDTO.getEmail() + "' already exists", 400);
    }
    UserEntity userEntity = UserMapper.toEntity(userDTO);
    return UserMapper.toDto(userRepository.save(userEntity));
  }

  public UserDTO updateUser(Long id, UserDTO userDTO) {
    UserEntity existingUser = userRepository.findById(id)
            .orElseThrow(() -> new TicketBookingException("User not found", 404));
    if (!userDTO.getEmail().equals(existingUser.getEmail())) {
      throw new TicketBookingException("Email cannot be changed", 400);
    }
    UserEntity userEntity = UserMapper.toEntity(userDTO);
    userEntity.setId(id);
    return UserMapper.toDto(userRepository.save(userEntity));
  }

  public boolean deleteUser(Long userId) {
    if (userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
      return true;
    }
    return false;
  }
}
