package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.UserAccountDTO;

import java.math.BigDecimal;

public interface UserAccountService {

   UserAccountDTO refillAccount(String userId, BigDecimal amount);
}