package com.tananushka.ticketbooking.repository.mongo;

import com.tananushka.ticketbooking.model.mongo.MongoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMongoRepository extends MongoRepository<MongoEvent, String> {
}
