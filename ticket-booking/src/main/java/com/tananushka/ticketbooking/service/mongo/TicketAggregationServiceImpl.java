package com.tananushka.ticketbooking.service.mongo;

import com.tananushka.ticketbooking.service.TicketAggregationService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketAggregationServiceImpl implements TicketAggregationService {

   private static final String TICKETS_SOLD = "ticketsSold";
   private static final String TICKETS = "tickets";
   private static final String EVENT_DETAILS = "eventDetails";
   private static final String EVENT_DETAILS_DATE = "eventDetails.date";
   private static final String EVENTS = "events";
   private static final String EVENT_ID = "eventId";
   private static final String ID = "_id";
   private static final String CATEGORY = "category";

   private final MongoTemplate mongoTemplate;

   @Override
   public List<Document> getGroupedByDate() {
      Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.lookup(EVENTS, EVENT_ID, ID, EVENT_DETAILS),
            Aggregation.unwind(EVENT_DETAILS),
            Aggregation.match(Criteria.where(EVENT_DETAILS_DATE).ne(null)),
            Aggregation.group(EVENT_DETAILS_DATE)
                  .count().as(TICKETS_SOLD),
            Aggregation.sort(Sort.Direction.DESC, TICKETS_SOLD)
      );

      AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, TICKETS, Document.class);
      return results.getMappedResults();
   }

   @Override
   public List<Document> getGroupedByCategory() {
      Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group(CATEGORY)
                  .count().as(TICKETS_SOLD),
            Aggregation.sort(Sort.Direction.DESC, TICKETS_SOLD)
      );
      AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, TICKETS, Document.class);
      return results.getMappedResults();
   }

   @Override
   public List<Document> getGroupedByCategoryAndDate() {
      Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.lookup(EVENTS, EVENT_ID, ID, EVENT_DETAILS),
            Aggregation.unwind(EVENT_DETAILS),
            Aggregation.match(Criteria.where(EVENT_DETAILS_DATE).ne(null)),
            Aggregation.group(CATEGORY, EVENT_DETAILS_DATE)
                  .count().as(TICKETS_SOLD),
            Aggregation.sort(Sort.Direction.DESC, TICKETS_SOLD)
      );
      AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, TICKETS, Document.class);
      return results.getMappedResults();
   }

}
