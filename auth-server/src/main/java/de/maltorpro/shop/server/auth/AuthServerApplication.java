package de.maltorpro.shop.server.auth;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.maltorpro.shop.infrastructure.utils.SSLUtils;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args)
            throws KeyManagementException, NoSuchAlgorithmException {

        String certificateCheck = System.getProperty("certificateCheck");

        if (StringUtils.equals(certificateCheck, "false")
                || StringUtils.equals(certificateCheck, "0")) {

            SSLUtils.turnOffSslChecking();
        }

        SpringApplication.run(AuthServerApplication.class, args);
    }

}