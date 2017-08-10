package de.maltorpro.shop.server.recource;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RessourceServerApplication {
	
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
	    return user;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RessourceServerApplication.class, args);
	}
}
