package com.meetmate.meetmatebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MeetmatebackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(MeetmatebackendApplication.class, args);
  }
}
