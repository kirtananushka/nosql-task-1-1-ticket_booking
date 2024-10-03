package com.tananushka.ticketbooking.service.mongo;

import com.tananushka.ticketbooking.dto.EventDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.EventMapper;
import com.tananushka.ticketbooking.model.mongo.MongoEvent;
import com.tananushka.ticketbooking.repository.mongo.EventMongoRepository;
import com.tananushka.ticketbooking.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("mongo")
public class EventMongoService implements EventService {

   private final EventMongoRepository eventRepository;

   @Override
   public EventDTO getEventById(String eventId) {
      return eventRepository.findById(eventId)
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
      MongoEvent mongoEvent = EventMapper.toMongoEntity(eventDTO);
      return EventMapper.toDto(eventRepository.save(mongoEvent));
   }

   @Override
   public EventDTO updateEvent(String id, EventDTO eventDTO) {
      MongoEvent mongoEvent = EventMapper.toMongoEntity(eventDTO);
      mongoEvent.setId(id);
      return EventMapper.toDto(eventRepository.save(mongoEvent));
   }

   @Override
   public boolean deleteEvent(String eventId) {
      if (eventRepository.existsById(eventId)) {
         eventRepository.deleteById(eventId);
         return true;
      }
      return false;
   }
}
