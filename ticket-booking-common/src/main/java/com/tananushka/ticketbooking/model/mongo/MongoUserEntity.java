package com.tananushka.ticketbooking.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class MongoUserEntity {
   @Id
   private String id;
   private String firstName;
   private String lastName;
   private String email;
   private MongoUserAccount userAccount;
}
