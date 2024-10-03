package com.tananushka.ticketbooking.mapper;

import com.tananushka.ticketbooking.dto.EventDTO;
import com.tananushka.ticketbooking.dto.TicketDTO;
import com.tananushka.ticketbooking.dto.UserDTO;
import com.tananushka.ticketbooking.model.mongo.MongoEvent;
import com.tananushka.ticketbooking.model.mongo.MongoTicket;
import com.tananushka.ticketbooking.model.mongo.MongoUserEntity;
import com.tananushka.ticketbooking.model.postgres.Ticket;
import com.tananushka.ticketbooking.repository.mongo.EventMongoRepository;
import com.tananushka.ticketbooking.repository.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper {

  private final UserMongoRepository userRepository;
  private final EventMongoRepository eventRepository;

  public static TicketDTO toDto(Ticket ticket) {
    if (ticket == null) return null;
    TicketDTO ticketDTO = new TicketDTO();
    ticketDTO.setId(String.valueOf(ticket.getId()));
    ticketDTO.setUser(UserMapper.toDto(ticket.getUserEntity()));
    ticketDTO.setEvent(EventMapper.toDto(ticket.getEvent()));
    ticketDTO.setSeatNumber(ticket.getSeatNumber());
    ticketDTO.setCategory(ticket.getCategory());
    return ticketDTO;
  }

  public static Ticket toEntity(TicketDTO ticketDTO) {
    if (ticketDTO == null) return null;
    Ticket ticket = new Ticket();
    ticket.setId(ticketDTO.getId() != null ? Long.valueOf(ticketDTO.getId()) : null);
    ticket.setUserEntity(UserMapper.toEntity(ticketDTO.getUser()));
    ticket.setEvent(EventMapper.toEntity(ticketDTO.getEvent()));
    ticket.setSeatNumber(ticketDTO.getSeatNumber());
    ticket.setCategory(ticketDTO.getCategory());
    return ticket;
  }

  public TicketDTO toDto(MongoTicket ticket) {
    if (ticket == null) return null;

    TicketDTO ticketDTO = new TicketDTO();
    ticketDTO.setId(ticket.getId());

    UserDTO userDTO = new UserDTO();
    userDTO.setId(String.valueOf(ticket.getUserId()));

    MongoUserEntity user = userRepository.findById(String.valueOf(ticket.getUserId())).orElse(null);
    if (user != null) {
      userDTO.setFirstName(user.getFirstName());
      userDTO.setLastName(user.getLastName());
      userDTO.setEmail(user.getEmail());
      userDTO.setUserAccount(UserAccountMapper.toDto(user.getUserAccount()));
    }
    ticketDTO.setUser(userDTO);

    EventDTO eventDTO = new EventDTO();
    eventDTO.setId(String.valueOf(ticket.getEventId()));

    MongoEvent event = eventRepository.findById(String.valueOf(ticket.getEventId())).orElse(null);
    if (event != null) {
      eventDTO.setTitle(event.getTitle());
      eventDTO.setDate(event.getDate());
      eventDTO.setTicketPrice(event.getTicketPrice());
    }
    ticketDTO.setEvent(eventDTO);
    ticketDTO.setSeatNumber(ticket.getSeatNumber());
    ticketDTO.setCategory(ticket.getCategory());
    return ticketDTO;
  }

  public MongoTicket toMongoEntity(TicketDTO ticketDTO) {
    if (ticketDTO == null) return null;
    MongoTicket ticket = new MongoTicket();
    ticket.setId(ticketDTO.getId());
    ticket.setUserId(new ObjectId(ticketDTO.getUser().getId()));
    ticket.setEventId(new ObjectId(ticketDTO.getEvent().getId()));
    ticket.setSeatNumber(ticketDTO.getSeatNumber());
    ticket.setCategory(ticketDTO.getCategory());
    return ticket;
  }
}
