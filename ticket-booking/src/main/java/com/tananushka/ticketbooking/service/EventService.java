package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.EventDTO;

import java.util.List;

public interface EventService {

   EventDTO getEventById(String eventId);

   List<EventDTO> getAllEvents();

   EventDTO createEvent(EventDTO eventDTO);

   EventDTO updateEvent(String eventId, EventDTO eventDTO);

   boolean deleteEvent(String eventId);
}
