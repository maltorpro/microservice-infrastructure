package de.maltorpro.shop.server.recource;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.maltorpro.shop.infrastructure.utils.SSLUtils;

@SpringBootApplication
@RestController
public class RessourceServerApplication {
	
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
	    return user;
	}
	
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {
		

		String certificateCheck =  System.getProperty("certificateCheck");
		
		if(StringUtils.equals(certificateCheck, "false") || StringUtils.equals(certificateCheck, "0")) {
			
			SSLUtils.turnOffSslChecking();
		}
		
		SpringApplication.run(RessourceServerApplication.class, args);
	}
}
