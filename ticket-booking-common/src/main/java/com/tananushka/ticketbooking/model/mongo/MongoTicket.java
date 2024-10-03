package com.tananushka.ticketbooking.model.mongo;

import com.tananushka.ticketbooking.model.Category;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
@Data
public class MongoTicket {
   @Id
   private String id;
   private ObjectId userId;
   private ObjectId eventId;
   private Integer seatNumber;
   private Category category;
}