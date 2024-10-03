package com.tananushka.ticketbooking.repository.postgres;

import com.tananushka.ticketbooking.model.postgres.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

   boolean existsByEmail(String email);
}
