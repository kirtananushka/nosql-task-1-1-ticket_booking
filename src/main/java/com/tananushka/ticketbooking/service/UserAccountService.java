package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.UserAccountDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.UserAccountMapper;
import com.tananushka.ticketbooking.model.UserAccount;
import com.tananushka.ticketbooking.model.UserEntity;
import com.tananushka.ticketbooking.repository.UserAccountRepository;
import com.tananushka.ticketbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountService {
  private final UserRepository userRepository;
  private final UserAccountRepository userAccountRepository;

  public UserAccountDTO refillAccount(Long userId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new TicketBookingException("The amount must be greater than zero", 400);
    }

    UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new TicketBookingException("User not found", 404));

    UserAccount userAccount = userEntity.getUserAccount();
    if (userAccount == null) {
      userAccount = new UserAccount();
      userAccount.setUserEntity(userEntity);
      userAccount.setBalance(BigDecimal.ZERO);
    }

    userAccount.setBalance(userAccount.getBalance().add(amount));
    userAccountRepository.save(userAccount);

    return UserAccountMapper.toDto(userAccount);
  }
}
