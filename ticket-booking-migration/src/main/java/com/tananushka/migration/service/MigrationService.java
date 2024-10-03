package com.tananushka.migration.service;

import com.tananushka.ticketbooking.model.Category;
import com.tananushka.ticketbooking.model.mongo.MongoEvent;
import com.tananushka.ticketbooking.model.mongo.MongoTicket;
import com.tananushka.ticketbooking.model.mongo.MongoUserAccount;
import com.tananushka.ticketbooking.model.mongo.MongoUserEntity;
import com.tananushka.ticketbooking.repository.mongo.EventMongoRepository;
import com.tananushka.ticketbooking.repository.mongo.TicketMongoRepository;
import com.tananushka.ticketbooking.repository.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MigrationService {

   private final DataMigrationService dataMigrationService;
   private final UserMongoRepository userEntityMongoRepository;
   private final EventMongoRepository eventMongoRepository;
   private final TicketMongoRepository ticketMongoRepository;

   public Map<String, String> migrateUsers() {
      List<Map<String, Object>> userEntities = dataMigrationService.fetchUsersFromPostgres();
      return userEntities.stream().collect(Collectors.toMap(
            row -> String.valueOf(row.get("id")),
            row -> {
               MongoUserEntity mongoUserEntity = new MongoUserEntity();
               mongoUserEntity.setFirstName((String) row.get("first_name"));
               mongoUserEntity.setLastName((String) row.get("last_name"));
               mongoUserEntity.setEmail((String) row.get("email"));

               MongoUserAccount account = new MongoUserAccount();
               account.setBalance((BigDecimal) row.get("balance"));
               mongoUserEntity.setUserAccount(account);

               MongoUserEntity savedUser = userEntityMongoRepository.save(mongoUserEntity);
               log.info("User saved with old ID: {} and new MongoDB ID: {}", row.get("id"), savedUser.getId());
               return savedUser.getId();
            }
      ));
   }

   public Map<String, String> migrateEvents() {
      List<Map<String, Object>> events = dataMigrationService.fetchEventsFromPostgres();
      return events.stream().collect(Collectors.toMap(
            row -> String.valueOf(row.get("id")),
            row -> {
               MongoEvent mongoEvent = new MongoEvent();
               mongoEvent.setTitle((String) row.get("title"));
               mongoEvent.setDate(((Timestamp) row.get("date")).toLocalDateTime());
               mongoEvent.setTicketPrice((BigDecimal) row.get("ticket_price"));

               MongoEvent savedEvent = eventMongoRepository.save(mongoEvent);
               log.info("Event saved with old ID: {} and new MongoDB ID: {}", row.get("id"), savedEvent.getId());
               return savedEvent.getId();
            }
      ));
   }

   public void migrateTickets(Map<String, String> userIdMap, Map<String, String> eventIdMap) {
      List<Map<String, Object>> tickets = dataMigrationService.fetchTicketsFromPostgres();
      tickets.forEach(row -> {
         log.info("Processing ticket with old User ID: {} and old Event ID: {}", row.get("user_id"), row.get("event_id"));
      });

      List<MongoTicket> ticketsToInsert = tickets.stream().map(row -> {
         MongoTicket mongoTicket = new MongoTicket();

         String mappedUserId = userIdMap.get(String.valueOf(row.get("user_id")));
         String mappedEventId = eventIdMap.get(String.valueOf(row.get("event_id")));

         if (mappedUserId == null || mappedEventId == null) {
            log.error("Error: Could not find matching User ID or Event ID for ticket with old User ID: {} and old Event ID: {}", row.get("user_id"), row.get("event_id"));
         }

         mongoTicket.setUserId(new ObjectId(mappedUserId));
         mongoTicket.setEventId(new ObjectId(mappedEventId));
         mongoTicket.setSeatNumber((Integer) row.get("seat_number"));
         mongoTicket.setCategory(Category.valueOf((String) row.get("category")));

         return mongoTicket;
      }).toList();

      ticketMongoRepository.saveAll(ticketsToInsert);
   }
}
