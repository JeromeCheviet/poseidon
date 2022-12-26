package com.poseidon.poseidon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Poseidon is an application to manage financials data.
 */
@SpringBootApplication
public class Application {

	/**
	 * Main method to launch application and spring context.
	 * @param args Default parameter
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
