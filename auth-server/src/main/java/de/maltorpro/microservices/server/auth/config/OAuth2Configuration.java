package de.maltorpro.microservices.server.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter jwtTokenEnhancer;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
	
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).tokenEnhancer(jwtTokenEnhancer).authenticationManager(authenticationManager);
    }
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer);
    }
	
	@Bean
	public JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "Olitokivu657".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }
    
    
    @Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
	 	clients.inMemory()
	        .withClient("my-trusted-client")
	            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
	            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ROLE_DEVELOPERS")
	            .scopes("read", "write", "trust")
	            .accessTokenValiditySeconds(3600)
	            .refreshTokenValiditySeconds(160)
		    .and()
	        .withClient("my-client-with-registered-redirect")
	            .authorizedGrantTypes("authorization_code")
	            .authorities("ROLE_CLIENT")
	            .scopes("read", "trust")
	            .redirectUris("http://anywhere?key=value")
		    .and()
	        .withClient("my-client-with-secret")
	            .authorizedGrantTypes("client_credentials", "password")
	            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
	            .scopes("read", "write")
	            .secret("secret");
	// @formatter:on
	}
}
