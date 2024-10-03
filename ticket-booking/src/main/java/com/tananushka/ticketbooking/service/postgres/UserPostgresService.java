package com.tananushka.ticketbooking.service.postgres;

import com.tananushka.ticketbooking.dto.UserDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.UserMapper;
import com.tananushka.ticketbooking.model.postgres.UserEntity;
import com.tananushka.ticketbooking.repository.postgres.UserRepository;
import com.tananushka.ticketbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("postgres")
public class UserPostgresService implements UserService {
   private final UserRepository userRepository;

   @Override
   public UserDTO getUserById(String userId) {
      return userRepository.findById(Long.valueOf(userId))
            .map(UserMapper::toDto)
            .orElseThrow(() -> new TicketBookingException("User not found", 404));
   }

   @Override
   public List<UserDTO> getAllUsers() {
      return userRepository.findAll()
            .stream()
            .map(UserMapper::toDto)
            .toList();
   }

   @Override
   public UserDTO createUser(UserDTO userDTO) {
      if (Boolean.TRUE.equals(userRepository.existsByEmail(userDTO.getEmail()))) {
         throw new TicketBookingException("User with email '" + userDTO.getEmail() + "' already exists", 400);
      }
      UserEntity userEntity = UserMapper.toEntity(userDTO);
      return UserMapper.toDto(userRepository.save(userEntity));
   }

   @Override
   public UserDTO updateUser(String id, UserDTO userDTO) {
      UserEntity existingUser = userRepository.findById(Long.valueOf(id))
            .orElseThrow(() -> new TicketBookingException("User not found", 404));

      if (userDTO.getFirstName() == null || userDTO.getLastName() == null) {
         throw new TicketBookingException("First name and last name cannot be null", 400);
      }

      if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())) {
         throw new TicketBookingException("Email cannot be changed", 400);
      }

      existingUser.setFirstName(userDTO.getFirstName());
      existingUser.setLastName(userDTO.getLastName());

      return UserMapper.toDto(userRepository.save(existingUser));
   }

   @Override
   public boolean deleteUser(String userId) {
      if (userRepository.existsById(Long.valueOf(userId))) {
         userRepository.deleteById(Long.valueOf(userId));
         return true;
      }
      return false;
   }
}
