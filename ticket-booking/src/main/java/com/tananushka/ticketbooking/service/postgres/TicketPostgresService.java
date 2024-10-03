package com.tananushka.ticketbooking.service.postgres;

import com.tananushka.ticketbooking.dto.TicketDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.TicketMapper;
import com.tananushka.ticketbooking.model.postgres.Event;
import com.tananushka.ticketbooking.model.postgres.Ticket;
import com.tananushka.ticketbooking.model.postgres.UserAccount;
import com.tananushka.ticketbooking.model.postgres.UserEntity;
import com.tananushka.ticketbooking.repository.postgres.EventRepository;
import com.tananushka.ticketbooking.repository.postgres.TicketRepository;
import com.tananushka.ticketbooking.repository.postgres.UserAccountRepository;
import com.tananushka.ticketbooking.repository.postgres.UserRepository;
import com.tananushka.ticketbooking.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("postgres")
public class TicketPostgresService implements TicketService {

   private final UserRepository userRepository;
   private final EventRepository eventRepository;
   private final TicketRepository ticketRepository;
   private final UserAccountRepository userAccountRepository;

   @Override
   public List<TicketDTO> getAllTickets() {
      return ticketRepository.findAll()
            .stream()
            .map(TicketMapper::toDto)
            .toList();
   }

   @Override
   public TicketDTO bookTicket(TicketDTO ticketDTO) {
      UserEntity userEntity = userRepository.findById(Long.valueOf(ticketDTO.getUser().getId()))
            .orElseThrow(() -> new TicketBookingException("User not found", 404));
      Event event = eventRepository.findById(Long.valueOf(ticketDTO.getEvent().getId()))
            .orElseThrow(() -> new TicketBookingException("Event not found", 404));

      boolean seatAlreadyBooked = ticketRepository.existsByEventIdAndSeatNumber(Long.valueOf(ticketDTO.getEvent().getId()), ticketDTO.getSeatNumber());
      if (seatAlreadyBooked) {
         throw new TicketBookingException("Seat number " + ticketDTO.getSeatNumber() + " is already booked for event " + ticketDTO.getEvent().getId(), 400);
      }

      UserAccount userAccount = userEntity.getUserAccount();
      if (userAccount.getBalance().compareTo(event.getTicketPrice()) < 0) {
         throw new TicketBookingException("User does not have enough balance to buy the ticket", 400);
      }

      userAccount.setBalance(userAccount.getBalance().subtract(event.getTicketPrice()));

      userAccountRepository.save(userAccount);

      Ticket ticket = TicketMapper.toEntity(ticketDTO);
      ticket.setUserEntity(userEntity);
      ticket.setEvent(event);

      Ticket savedTicket = ticketRepository.save(ticket);

      return TicketMapper.toDto(savedTicket);
   }

   @Override
   @Transactional
   public boolean cancelTicket(String ticketId) {
      Ticket ticket = ticketRepository.findById(Long.valueOf(ticketId))
            .orElseThrow(() -> new TicketBookingException("Ticket not found", 404));

      UserAccount userAccount = ticket.getUserEntity().getUserAccount();
      Event event = ticket.getEvent();

      userAccount.setBalance(userAccount.getBalance().add(event.getTicketPrice()));

      userAccountRepository.save(userAccount);

      ticketRepository.deleteById(Long.valueOf(ticketId));

      return true;
   }

}
