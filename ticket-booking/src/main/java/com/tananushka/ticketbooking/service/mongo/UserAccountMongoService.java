package com.tananushka.ticketbooking.service.mongo;

import com.tananushka.ticketbooking.dto.UserAccountDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.UserAccountMapper;
import com.tananushka.ticketbooking.model.mongo.MongoUserAccount;
import com.tananushka.ticketbooking.model.mongo.MongoUserEntity;
import com.tananushka.ticketbooking.repository.mongo.UserMongoRepository;
import com.tananushka.ticketbooking.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("mongo")
public class UserAccountMongoService implements UserAccountService {

   private final UserMongoRepository userRepository;

   @Override
   public UserAccountDTO refillAccount(String userId, BigDecimal amount) {
      if (amount.compareTo(BigDecimal.ZERO) <= 0) {
         throw new TicketBookingException("The amount must be greater than zero", 400);
      }

      MongoUserEntity mongoUserEntity = userRepository.findById(userId)
            .orElseThrow(() -> new TicketBookingException("User not found", 404));

      MongoUserAccount mongoUserAccount = mongoUserEntity.getUserAccount();
      if (mongoUserAccount == null) {
         mongoUserAccount = new MongoUserAccount();
         mongoUserAccount.setBalance(BigDecimal.ZERO);
         mongoUserEntity.setUserAccount(mongoUserAccount);
      }

      mongoUserAccount.setBalance(mongoUserAccount.getBalance().add(amount));
      userRepository.save(mongoUserEntity);
      return UserAccountMapper.toDto(mongoUserAccount);
   }
}
