package com.tananushka.ticketbooking.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "events")
@Data
public class MongoEvent {
   @Id
   private String id;
   private String title;
   private LocalDateTime date;
   private BigDecimal ticketPrice;
}
