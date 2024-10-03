package com.tananushka.ticketbooking.controller;

import com.tananushka.ticketbooking.service.TicketAggregationService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets/aggregation")
@RequiredArgsConstructor
public class AggregationController {

   private final TicketAggregationService ticketAggregationService;

   @GetMapping("/group-by-date")
   public ResponseEntity<List<Document>> getTicketsGroupedByDate() {
      List<Document> groupedResults = ticketAggregationService.getGroupedByDate();
      return new ResponseEntity<>(groupedResults, HttpStatus.OK);
   }

   @GetMapping("/group-by-category")
   public ResponseEntity<List<Document>> getTicketsGroupedByCategory() {
      List<Document> groupedResults = ticketAggregationService.getGroupedByCategory();
      return new ResponseEntity<>(groupedResults, HttpStatus.OK);
   }

   @GetMapping("/group-by-category-and-date")
   public ResponseEntity<List<Document>> getTicketsGroupedByCategoryAndDate() {
      List<Document> groupedResults = ticketAggregationService.getGroupedByCategoryAndDate();
      return new ResponseEntity<>(groupedResults, HttpStatus.OK);
   }
}
