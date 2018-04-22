package de.maltorpro.shop.server.auth.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("sha", new LdapShaPasswordEncoder());

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * http .authorizeRequests() .anyRequest().fullyAuthenticated() .and()
         * .formLogin();
         */

        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response
                                .sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests().antMatchers("/**").authenticated()
                .and().httpBasic();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
         * auth.inMemoryAuthentication().withUser("ben")
         * .password(passwordEncoder().encode("benspassword"))
         * .roles("DEVELOPERS").and().withUser("bob")
         * .password(passwordEncoder().encode("benspassword")) .roles("USER");
         */

        auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups").contextSource(contextSource())
                .passwordCompare().passwordEncoder(passwordEncoder())
                .passwordAttribute("userPassword");
    }

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(
                Arrays.asList("ldap://localhost:8389/"),
                "dc=springframework,dc=org");
    }
}