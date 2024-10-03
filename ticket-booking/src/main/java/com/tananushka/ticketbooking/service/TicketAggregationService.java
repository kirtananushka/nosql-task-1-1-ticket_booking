package com.tananushka.ticketbooking.service;

import org.bson.Document;

import java.util.List;

public interface TicketAggregationService {
   List<Document> getGroupedByDate();

   List<Document> getGroupedByCategory();

   List<Document> getGroupedByCategoryAndDate();
}
