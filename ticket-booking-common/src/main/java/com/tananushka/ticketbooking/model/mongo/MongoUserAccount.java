package com.tananushka.ticketbooking.model.mongo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MongoUserAccount {
   private BigDecimal balance;
}