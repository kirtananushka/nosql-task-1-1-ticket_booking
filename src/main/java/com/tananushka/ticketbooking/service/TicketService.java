package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.TicketDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.TicketMapper;
import com.tananushka.ticketbooking.model.Event;
import com.tananushka.ticketbooking.model.Ticket;
import com.tananushka.ticketbooking.model.UserAccount;
import com.tananushka.ticketbooking.model.UserEntity;
import com.tananushka.ticketbooking.repository.EventRepository;
import com.tananushka.ticketbooking.repository.TicketRepository;
import com.tananushka.ticketbooking.repository.UserAccountRepository;
import com.tananushka.ticketbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

  private final UserRepository userRepository;
  private final EventRepository eventRepository;
  private final TicketRepository ticketRepository;
  private final UserAccountRepository userAccountRepository;

  public List<TicketDTO> getAllTickets() {
    return ticketRepository.findAll()
            .stream()
            .map(TicketMapper::toDto)
            .toList();
  }

  public TicketDTO bookTicket(TicketDTO ticketDTO) {
    UserEntity userEntity = userRepository.findById(ticketDTO.getUser().getId())
            .orElseThrow(() -> new TicketBookingException("User not found", 404));
    Event event = eventRepository.findById(ticketDTO.getEvent().getId())
            .orElseThrow(() -> new TicketBookingException("Event not found", 404));

    boolean seatAlreadyBooked = ticketRepository.existsByEventIdAndSeatNumber(ticketDTO.getEvent().getId(), ticketDTO.getSeatNumber());
    if (seatAlreadyBooked) {
      throw new TicketBookingException("Seat number " + ticketDTO.getSeatNumber() + " is already booked for event " + ticketDTO.getEvent().getId());
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

  @Transactional
  public boolean cancelTicket(Long ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketBookingException("Ticket not found", 404));

    UserAccount userAccount = ticket.getUserEntity().getUserAccount();
    Event event = ticket.getEvent();

    userAccount.setBalance(userAccount.getBalance().add(event.getTicketPrice()));

    userAccountRepository.save(userAccount);

    ticketRepository.deleteById(ticketId);

    return true;
  }

}
