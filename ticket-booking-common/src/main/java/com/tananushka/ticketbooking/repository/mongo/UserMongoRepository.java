package com.tananushka.ticketbooking.repository.mongo;

import com.tananushka.ticketbooking.model.mongo.MongoUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<MongoUserEntity, String> {

   boolean existsByEmail(String email);
}
