package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PurebaDataBaseApplication {
	private static final Logger Log = LoggerFactory.getLogger(PurebaDataBaseApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PurebaDataBaseApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			Log.info("Customers found with findAll():");
			Log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				Log.info(customer.toString());
			}
			Log.info("");
			// fetch an individual customer by ID

			Customer customer = repository.findById(1L);
			Log.info("Customer found with findById(1L):");
			Log.info("--------------------------------");
			Log.info(customer.toString());
			Log.info("");
			// fetch customers by last name
			Log.info("Customer found with findByLastName('Bauer'):");
			Log.info("--------------------------------------------");
			for (Customer bauer : repository.findByLastName("Bauer")) {
				Log.info(bauer.toString());
			}
			Log.info("");
			Log.info("");
		};
	}
}
