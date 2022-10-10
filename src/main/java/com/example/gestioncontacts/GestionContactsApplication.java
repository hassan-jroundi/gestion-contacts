package com.example.gestioncontacts;

import com.example.gestioncontacts.repository.IContactRepository;
import com.example.gestioncontacts.repository.IEntrepriseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionContactsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionContactsApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IContactRepository contactRepository, IEntrepriseRepository entrepriseRepository) {
		return (args) -> {

		};
	}

}
