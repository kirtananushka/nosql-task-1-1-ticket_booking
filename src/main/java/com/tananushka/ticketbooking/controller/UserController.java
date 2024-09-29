package com.tananushka.ticketbooking.controller;

import com.tananushka.ticketbooking.dto.RefillRequestDTO;
import com.tananushka.ticketbooking.dto.UserAccountDTO;
import com.tananushka.ticketbooking.dto.UserDTO;
import com.tananushka.ticketbooking.service.UserAccountService;
import com.tananushka.ticketbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final UserAccountService userAccountService;

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> findAllUsers() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
    return new ResponseEntity<>(userService.updateUser(id, userDTO), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    boolean isDeleted = userService.deleteUser(id);
    return isDeleted ? new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT) :
            new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
  }

  @PostMapping("/{id}/refill")
  public ResponseEntity<UserAccountDTO> refillAccount(@PathVariable Long id, @RequestBody RefillRequestDTO refillRequest) {
    UserAccountDTO userAccountDTO = userAccountService.refillAccount(id, refillRequest.getAmount());
    return new ResponseEntity<>(userAccountDTO, HttpStatus.OK);
  }
}
