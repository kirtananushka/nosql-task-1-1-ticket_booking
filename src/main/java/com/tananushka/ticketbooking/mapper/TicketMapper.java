package com.tananushka.ticketbooking.mapper;

import com.tananushka.ticketbooking.dto.TicketDTO;
import com.tananushka.ticketbooking.model.Ticket;

public class TicketMapper {

  private TicketMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static TicketDTO toDto(Ticket ticket) {
    if (ticket == null) return null;

    TicketDTO ticketDTO = new TicketDTO();
    ticketDTO.setId(ticket.getId());
    ticketDTO.setUser(UserMapper.toDto(ticket.getUserEntity()));
    ticketDTO.setEvent(EventMapper.toDto(ticket.getEvent()));
    ticketDTO.setSeatNumber(ticket.getSeatNumber());
    ticketDTO.setCategory(ticket.getCategory());

    return ticketDTO;
  }

  public static Ticket toEntity(TicketDTO ticketDTO) {
    if (ticketDTO == null) return null;

    Ticket ticket = new Ticket();
    ticket.setId(ticketDTO.getId());
    ticket.setUserEntity(UserMapper.toEntity(ticketDTO.getUser()));
    ticket.setEvent(EventMapper.toEntity(ticketDTO.getEvent()));
    ticket.setSeatNumber(ticketDTO.getSeatNumber());
    ticket.setCategory(ticketDTO.getCategory());

    return ticket;
  }
}
