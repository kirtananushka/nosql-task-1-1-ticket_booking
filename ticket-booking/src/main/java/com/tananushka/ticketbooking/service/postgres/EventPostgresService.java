package com.tananushka.ticketbooking.service.postgres;

import com.tananushka.ticketbooking.dto.EventDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.EventMapper;
import com.tananushka.ticketbooking.model.postgres.Event;
import com.tananushka.ticketbooking.repository.postgres.EventRepository;
import com.tananushka.ticketbooking.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("postgres")
public class EventPostgresService implements EventService {
   private final EventRepository eventRepository;

   @Override
   public EventDTO getEventById(String eventId) {
      return eventRepository.findById(Long.valueOf(eventId))
            .map(EventMapper::toDto)
            .orElseThrow(() -> new TicketBookingException("Event not found", 404));
   }

   @Override
   public List<EventDTO> getAllEvents() {
      return eventRepository.findAll()
            .stream()
            .map(EventMapper::toDto)
            .toList();
   }

   @Override
   public EventDTO createEvent(EventDTO eventDTO) {
      Event event = EventMapper.toEntity(eventDTO);
      return EventMapper.toDto(eventRepository.save(event));
   }

   @Override
   public EventDTO updateEvent(String id, EventDTO eventDTO) {
      Event event = EventMapper.toEntity(eventDTO);
      event.setId(Long.valueOf(id));
      return EventMapper.toDto(eventRepository.save(event));
   }

   @Override
   public boolean deleteEvent(String eventId) {
      if (eventRepository.existsById(Long.valueOf(eventId))) {
         eventRepository.deleteById(Long.valueOf(eventId));
         return true;
      }
      return false;
   }
}
