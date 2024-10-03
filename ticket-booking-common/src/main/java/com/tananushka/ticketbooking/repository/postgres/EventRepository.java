package com.tananushka.ticketbooking.repository.postgres;

import com.tananushka.ticketbooking.model.postgres.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
