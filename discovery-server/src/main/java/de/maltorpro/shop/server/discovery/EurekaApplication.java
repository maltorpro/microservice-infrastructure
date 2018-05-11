package de.maltorpro.shop.server.discovery;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import de.maltorpro.shop.infrastructure.utils.SSLUtils;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class EurekaApplication {

    public static void main(String[] args)
            throws KeyManagementException, NoSuchAlgorithmException {

        String certificateCheck = System.getProperty("certificateCheck");

        if (StringUtils.equals(certificateCheck, "false")
                || StringUtils.equals(certificateCheck, "0")) {

            SSLUtils.turnOffSslChecking();
        }

        SpringApplication.run(EurekaApplication.class, args);
    }

}
