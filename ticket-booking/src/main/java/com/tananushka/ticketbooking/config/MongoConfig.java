package com.tananushka.ticketbooking.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("mongo")
@Configuration
@EnableAutoConfiguration(exclude = {
      DataSourceAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class
})
public class MongoConfig {
}
