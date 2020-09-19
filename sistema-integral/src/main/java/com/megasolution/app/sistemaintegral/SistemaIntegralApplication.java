package com.megasolution.app.sistemaintegral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication

public class SistemaIntegralApplication implements CommandLineRunner {


	// TODO: Solo modo desarrollo
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SistemaIntegralApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = "mfuhr";
		String password1 = "leo";


		for(int i = 0; i < 1; i++){
			String bcryptPassword = this.passwordEncoder.encode(password);
			String bcryptPassword1 = this.passwordEncoder.encode(password1);
			
			
			System.out.println("#################################################:     ->  " + bcryptPassword);
			System.out.println("#################################################:     ->  " + bcryptPassword1);
			
		}

	}
	
	
}
