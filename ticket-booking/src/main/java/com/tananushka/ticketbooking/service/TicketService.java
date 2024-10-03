package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.TicketDTO;

import java.util.List;

public interface TicketService {

   List<TicketDTO> getAllTickets();

   TicketDTO bookTicket(TicketDTO ticketDTO);

   boolean cancelTicket(String ticketId);
}
