package net.local.rentacar;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.local.rentacar.domain.entities.Car;
import net.local.rentacar.domain.entities.Customer;
import net.local.rentacar.domain.repositories.CarRepository;
import net.local.rentacar.domain.repositories.CustomerRepository;
import net.local.rentacar.domain.vo.CarCategory;
import net.local.rentacar.domain.vo.CarStatus;

@SpringBootApplication
public class RentacarApp {

    private static Logger log = LoggerFactory.getLogger(RentacarApp.class);

    public static void main(String[] args) {
		SpringApplication.run(RentacarApp.class, args);
	}
    
    @Bean
    CommandLineRunner initDatabase(CarRepository carRepository, CustomerRepository customerRepository) {
        return args -> {
            log.info("Initializing CAR database...");
            carRepository.save(Car.of(UUID.fromString("c8074995-3ca9-4b47-b888-182a585147b7"),"MCJ-1111", CarStatus.AVAILABLE, CarCategory.ECONOMY));
            carRepository.save(Car.of(UUID.fromString("bf5f8b5e-9398-45bd-8054-2d216e1d5006"),"WKJ-2222", CarStatus.AVAILABLE, CarCategory.INTERMEDIATE));

            log.info("Initializing CUSTOMER database...");
            customerRepository.save(Customer.of(UUID.fromString("388d56c3-6238-4823-b8c7-70917022776c"), "Jhon Doe", "123.456.789-00", 100));
            customerRepository.save(Customer.of(UUID.fromString("f421c190-11f6-431d-a7cc-2ab4f8652072"), "Max Steel", "987.654.321-11", 1000));
        };
    }
}
