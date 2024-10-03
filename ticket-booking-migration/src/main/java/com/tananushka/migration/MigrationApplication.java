package com.tananushka.migration;

import com.tananushka.migration.service.MigrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import java.util.Map;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.tananushka.ticketbooking.repository.mongo")
public class MigrationApplication {
   public static void main(String[] args) {
      SpringApplication.run(MigrationApplication.class, args);
   }
}

@Component
@RequiredArgsConstructor
@Slf4j
class MigrationJob implements CommandLineRunner {
   private final MigrationService migrationService;

   @Override
   public void run(String... args) {
      log.info("Starting data migration...");

      Map<String, String> migrateUsers = migrationService.migrateUsers();
      log.info("Users migrated successfully");

      Map<String, String> migrateEvents = migrationService.migrateEvents();
      log.info("Events migrated successfully");

      migrationService.migrateTickets(migrateUsers, migrateEvents);
      log.info("Tickets migrated successfully");

      log.info("Data migration completed");
   }
}