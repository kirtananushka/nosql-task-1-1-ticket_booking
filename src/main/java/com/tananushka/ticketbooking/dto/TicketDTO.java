package com.tananushka.ticketbooking.dto;

import com.tananushka.ticketbooking.model.Category;
import lombok.Data;

@Data
public class TicketDTO {
  private Long id;
  private UserDTO user;
  private EventDTO event;
  private Integer seatNumber;
  private Category category;
}
