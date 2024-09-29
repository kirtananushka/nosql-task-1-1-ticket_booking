package com.tananushka.ticketbooking.controller;

import com.tananushka.ticketbooking.dto.TicketDTO;
import com.tananushka.ticketbooking.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
  private final TicketService ticketService;

  @GetMapping
  public ResponseEntity<List<TicketDTO>> findAllTickets() {
    return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TicketDTO> bookTicket(@RequestBody TicketDTO ticketDTO) {
    return new ResponseEntity<>(ticketService.bookTicket(ticketDTO), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> cancelTicket(@PathVariable Long id) {
    boolean isCanceled = ticketService.cancelTicket(id);
    return isCanceled ? new ResponseEntity<>("Ticket canceled successfully", HttpStatus.NO_CONTENT) :
            new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
  }
}
