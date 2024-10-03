package com.tananushka.migration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataMigrationService {
   private final JdbcTemplate jdbcTemplate;

   public List<Map<String, Object>> fetchUsersFromPostgres() {
      String sql = """
                SELECT u.id, u.first_name, u.last_name, u.email, a.balance
                FROM users u
                LEFT JOIN user_accounts a ON u.id = a.user_id;
            """;
      return jdbcTemplate.queryForList(sql);
   }

   public List<Map<String, Object>> fetchEventsFromPostgres() {
      String sql = """
                SELECT id, title, date, ticket_price
                FROM events;
            """;
      return jdbcTemplate.queryForList(sql);
   }

   public List<Map<String, Object>> fetchTicketsFromPostgres() {
      String sql = """
                SELECT t.id, t.user_id, t.event_id, t.seat_number, t.category
                FROM tickets t;
            """;
      return jdbcTemplate.queryForList(sql);
   }
}
