package de.maltorpro.shop.server.auth.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswodEcoderTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        System.out.println(b.encode("secret"));

    }

}
