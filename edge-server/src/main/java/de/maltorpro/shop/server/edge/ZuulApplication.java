package de.maltorpro.shop.server.edge;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;

import de.maltorpro.shop.infrastructure.utils.SSLUtils;

@SpringBootApplication
@Controller
@EnableZuulProxy
@EnableResourceServer
@ComponentScan({ "de.maltorpro.shop.server.edge.configuration" })
public class ZuulApplication {

    public static void main(String[] args)
            throws KeyManagementException, NoSuchAlgorithmException {

        String certificateCheckProp = System.getProperty("certificateCheck");
        String certificateCheckEnv = System.getenv("CERTIFICATE_CHECK");

        if (StringUtils.equals(certificateCheckProp, "false")
                || StringUtils.equals(certificateCheckProp, "0")
                || StringUtils.equals(certificateCheckEnv, "false")
                || StringUtils.equals(certificateCheckEnv, "0")) {

            SSLUtils.turnOffSslChecking();
        }

        new SpringApplicationBuilder(ZuulApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
