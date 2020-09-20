package com.megasolution.app.sistemaintegral;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class SistemaIntegralApplication implements CommandLineRunner {


	/* Solo modo desarrollo
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	*/

	public static void main(String[] args) {
		SpringApplication.run(SistemaIntegralApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
	/* 	String password = "2744mfuhR";


		for(int i = 0; i < 1; i++){
			String bcryptPassword = this.passwordEncoder.encode(password);
			
			
			System.out.println("#################################################:     ->  " + bcryptPassword);
			
		} */

	}
	
	
}
