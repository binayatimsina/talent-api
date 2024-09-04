package com.example.talent_api.security;


import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//  Establish this application as an Authorization Server.
//  Principals are expected to be authenticated with HTTP basic.
//  Authenticated calls to /token return a JWT signed with a private key.

@Configuration
@EnableWebSecurity
public class Tsec {
    @Value("${rsa.public-key}") RSAPublicKey publicKey;

    private JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }


     @Bean
     public SecurityFilterChain securityFilterChainServer(HttpSecurity http) throws Exception {
        System.out.println("\n\n\n\n\n\n I am binaya");
        System.out.println();
         return http
             //  Disable sessions.  We want a stateless application:
             .sessionManagement(
                 session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

             //  CSRF protection is merely extra overhead with session management disabled:
             .csrf(csrf -> csrf.disable())

             //  All inbound requests must be authenticated:
             .authorizeHttpRequests( auth -> auth
                 .requestMatchers("/").permitAll()
                 .anyRequest().authenticated()
             )
             
             .oauth2ResourceServer((resourceServer) ->
                resourceServer.jwt((customizer) ->
                    customizer.decoder(jwtDecoder())
                )
             
             )

             .build();
     }
}
