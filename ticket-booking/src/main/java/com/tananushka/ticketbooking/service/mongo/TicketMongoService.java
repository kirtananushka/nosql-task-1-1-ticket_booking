package com.tananushka.ticketbooking.service.mongo;

import com.tananushka.ticketbooking.dto.TicketDTO;
import com.tananushka.ticketbooking.exception.TicketBookingException;
import com.tananushka.ticketbooking.mapper.TicketMapper;
import com.tananushka.ticketbooking.model.mongo.MongoEvent;
import com.tananushka.ticketbooking.model.mongo.MongoTicket;
import com.tananushka.ticketbooking.model.mongo.MongoUserAccount;
import com.tananushka.ticketbooking.model.mongo.MongoUserEntity;
import com.tananushka.ticketbooking.repository.mongo.EventMongoRepository;
import com.tananushka.ticketbooking.repository.mongo.TicketMongoRepository;
import com.tananushka.ticketbooking.repository.mongo.UserMongoRepository;
import com.tananushka.ticketbooking.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("mongo")
public class TicketMongoService implements TicketService {

   private final UserMongoRepository userRepository;
   private final EventMongoRepository eventRepository;
   private final TicketMongoRepository ticketRepository;
   private final TicketMapper ticketMapper;

   @Override
   public List<TicketDTO> getAllTickets() {
      return ticketRepository.findAll()
            .stream()
            .map(ticketMapper::toDto)
            .toList();
   }

   @Override
   public TicketDTO bookTicket(TicketDTO ticketDTO) {
      ObjectId userId = new ObjectId(ticketDTO.getUser().getId());
      ObjectId eventId = new ObjectId(ticketDTO.getEvent().getId());
      Integer seatNumber = ticketDTO.getSeatNumber();

      MongoUserEntity mongoUserEntity = userRepository.findById(userId.toString())
            .orElseThrow(() -> new TicketBookingException("User not found", 404));

      MongoEvent mongoEvent = eventRepository.findById(eventId.toString())
            .orElseThrow(() -> new TicketBookingException("Event not found", 404));

      Boolean seatAlreadyBooked = ticketRepository.existsByEventIdAndSeatNumber(eventId.toString(), seatNumber);
      if (Boolean.TRUE.equals(seatAlreadyBooked)) {
         throw new TicketBookingException("Seat number " + seatNumber + " is already booked for event " + eventId, 400);
      }

      MongoUserAccount mongoUserAccount = mongoUserEntity.getUserAccount();
      if (mongoUserAccount == null || mongoUserAccount.getBalance().compareTo(mongoEvent.getTicketPrice()) < 0) {
         throw new TicketBookingException("User does not have enough balance to buy the ticket", 400);
      }

      mongoUserAccount.setBalance(mongoUserAccount.getBalance().subtract(mongoEvent.getTicketPrice()));
      userRepository.save(mongoUserEntity);

      MongoTicket mongoTicket = ticketMapper.toMongoEntity(ticketDTO);
      mongoTicket.setUserId(userId);
      mongoTicket.setEventId(eventId);

      return ticketMapper.toDto(ticketRepository.save(mongoTicket));
   }

   @Override
   @Transactional
   public boolean cancelTicket(String ticketId) {
      MongoTicket mongoTicket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketBookingException("Ticket not found", 404));

      MongoUserEntity mongoUserEntity = userRepository.findById(String.valueOf(mongoTicket.getUserId()))
            .orElseThrow(() -> new TicketBookingException("User not found", 404));

      MongoEvent mongoEvent = eventRepository.findById(String.valueOf(mongoTicket.getEventId()))
            .orElseThrow(() -> new TicketBookingException("Event not found", 404));

      MongoUserAccount mongoUserAccount = mongoUserEntity.getUserAccount();
      mongoUserAccount.setBalance(mongoUserAccount.getBalance().add(mongoEvent.getTicketPrice()));
      userRepository.save(mongoUserEntity);

      ticketRepository.deleteById(ticketId);
      return true;
   }
}
