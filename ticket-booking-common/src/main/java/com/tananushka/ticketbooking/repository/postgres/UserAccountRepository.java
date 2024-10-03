package com.tananushka.ticketbooking.repository.postgres;

import com.tananushka.ticketbooking.model.postgres.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
