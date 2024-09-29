package com.tananushka.ticketbooking.service;

import com.tananushka.ticketbooking.dto.EventDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.EventMapper;
import com.tananushka.ticketbooking.model.Event;
import com.tananushka.ticketbooking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;

  public EventDTO getEventById(Long eventId) {
    return eventRepository.findById(eventId)
            .map(EventMapper::toDto)
            .orElseThrow(() -> new TicketBookingException("Event not found", 404));
  }

  public List<EventDTO> getAllEvents() {
    return eventRepository.findAll()
            .stream()
            .map(EventMapper::toDto)
            .toList();
  }

  public EventDTO createEvent(EventDTO eventDTO) {
    Event event = EventMapper.toEntity(eventDTO);
    return EventMapper.toDto(eventRepository.save(event));
  }

  public EventDTO updateEvent(Long id, EventDTO eventDTO) {
    Event event = EventMapper.toEntity(eventDTO);
    event.setId(id);
    return EventMapper.toDto(eventRepository.save(event));
  }

  public boolean deleteEvent(Long eventId) {
    if (eventRepository.existsById(eventId)) {
      eventRepository.deleteById(eventId);
      return true;
    }
    return false;
  }
}
