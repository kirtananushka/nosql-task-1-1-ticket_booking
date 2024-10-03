package com.tananushka.ticketbooking.repository.mongo;

import com.tananushka.ticketbooking.model.mongo.MongoTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface TicketMongoRepository extends MongoRepository<MongoTicket, String> {

   @Query(value = "{ 'eventId': ?0, 'seatNumber': ?1 }", exists = true)
   boolean existsByEventIdAndSeatNumber(String eventId, Integer seatNumber);
}

