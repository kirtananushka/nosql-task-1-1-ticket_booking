package com.tananushka.ticketbooking.service.postgres;

import com.tananushka.ticketbooking.dto.UserAccountDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.UserAccountMapper;
import com.tananushka.ticketbooking.model.postgres.UserAccount;
import com.tananushka.ticketbooking.model.postgres.UserEntity;
import com.tananushka.ticketbooking.repository.postgres.UserAccountRepository;
import com.tananushka.ticketbooking.repository.postgres.UserRepository;
import com.tananushka.ticketbooking.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("postgres")
public class UserAccountPostgresService implements UserAccountService {
   private final UserRepository userRepository;
   private final UserAccountRepository userAccountRepository;

   @Override
   public UserAccountDTO refillAccount(String userId, BigDecimal amount) {
      if (amount.compareTo(BigDecimal.ZERO) <= 0) {
         throw new TicketBookingException("The amount must be greater than zero", 400);
      }

      UserEntity userEntity = userRepository.findById(Long.valueOf(userId))
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
