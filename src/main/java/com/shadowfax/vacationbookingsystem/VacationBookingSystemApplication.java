package com.shadowfax.vacationbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VacationBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacationBookingSystemApplication.class, args);
	}

}
