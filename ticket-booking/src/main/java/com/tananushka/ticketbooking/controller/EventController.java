package com.tananushka.ticketbooking.controller;

import com.tananushka.ticketbooking.dto.EventDTO;
import com.tananushka.ticketbooking.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
  private final EventService eventService;

  @GetMapping("/{id}")
  public ResponseEntity<EventDTO> findEventById(@PathVariable("id") String id) {
    return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<EventDTO>> findAllEvents() {
    return new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
    return new ResponseEntity<>(eventService.createEvent(eventDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EventDTO> updateEvent(@PathVariable("id") String id, @RequestBody EventDTO eventDTO) {
    return new ResponseEntity<>(eventService.updateEvent(id, eventDTO), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEvent(@PathVariable("id") String id) {
    boolean isDeleted = eventService.deleteEvent(id);
    return isDeleted ? new ResponseEntity<>("Event deleted successfully", HttpStatus.NO_CONTENT) :
            new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
  }
}
