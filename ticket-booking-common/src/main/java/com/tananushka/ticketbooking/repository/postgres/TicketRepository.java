package com.tananushka.ticketbooking.repository.postgres;

import com.tananushka.ticketbooking.model.postgres.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  boolean existsByEventIdAndSeatNumber(Long eventId, Integer seatNumber);
}
