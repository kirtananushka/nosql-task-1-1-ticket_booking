package com.tananushka.ticketbooking.mapper;

import com.tananushka.ticketbooking.dto.EventDTO;
import com.tananushka.ticketbooking.model.Event;

public class EventMapper {

  private EventMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static EventDTO toDto(Event event) {
    if (event == null) return null;

    EventDTO eventDTO = new EventDTO();
    eventDTO.setId(event.getId());
    eventDTO.setTitle(event.getTitle());
    eventDTO.setDate(event.getDate());
    eventDTO.setTicketPrice(event.getTicketPrice());

    return eventDTO;
  }

  public static Event toEntity(EventDTO eventDTO) {
    if (eventDTO == null) return null;

    Event event = new Event();
    event.setId(eventDTO.getId());
    event.setTitle(eventDTO.getTitle());
    event.setDate(eventDTO.getDate());
    event.setTicketPrice(eventDTO.getTicketPrice());

    return event;
  }
}
