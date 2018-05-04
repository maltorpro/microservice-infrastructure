package de.maltorpro.shop.server.config;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import de.maltorpro.shop.infrastructure.utils.SSLUtils;



@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {


	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {
		

		String certificateCheck =  System.getProperty("certificateCheck");
		
		if(StringUtils.equals(certificateCheck, "false") || StringUtils.equals(certificateCheck, "0")) {
			
			SSLUtils.turnOffSslChecking();
		}
		
		SpringApplication.run(ConfigServerApplication.class, args);

	}

}
