package com.adriforczek.vehicles;

import com.adriforczek.vehicles.domain.manufacturer.Manufacturer;
import com.adriforczek.vehicles.domain.manufacturer.ManufacturerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class VehicleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleApiApplication.class, args);
	}

	/**
	 * Initializes the car manufacturers available to the Vehicle API.
	 * @param repository where the manufacturer information persists.
	 * @return the car manufacturers to add to the related repository
	 */
	@Bean
	CommandLineRunner initDatabase(ManufacturerRepository repository) {
		return args -> {
			repository.save(new Manufacturer(100, "Audi"));
			repository.save(new Manufacturer(101, "Chevrolet"));
			repository.save(new Manufacturer(102, "Ford"));
			repository.save(new Manufacturer(103, "BMW"));
			repository.save(new Manufacturer(104, "Dodge"));
		};
	}

	}
