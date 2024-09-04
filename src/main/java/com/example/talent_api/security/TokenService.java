package com.example.talent_api.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.example.talent_api.model.User;
import com.example.talent_api.service.UserService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;


//  This service produces JWTs and signs them with a private key.
//  Most of the work is done by the NimbusJwtEncoder from Spring Security.
//  JWTs contain claims including the user name (sub) and authorities (scope)

@Service
public class TokenService implements UserDetailsService{

    @Value("${rsa.private-key}") RSAPrivateKey privateKey;
    @Value("${rsa.public-key}") RSAPublicKey publicKey;

    @Autowired
    private UserService userService;

    private final JwtDecoder jwtDecoder;

    public TokenService() {
        this.jwtDecoder = JwtDecoders.fromIssuerLocation("http://localhost:8080");
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        //  Create a space-delimited String containing all of the 
        //  principal's authorities.  These will become the "scope" inside the JWT:
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    
        //  Build a set of Claims to go inside the JWT.  Claims include 
        //  subject (the principal's username),  issue date of the token, 
        //  expiration time, and scopes, which map to the authorities.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        //  The NimbusJwtEncoder creates and encodes the JWT 
        //  from the claims.  It also signs it using the private key: 
        return encoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private JwtEncoder encoder() {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }


    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username, null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getType()) // Adjust based on your User roles/authorities setup
                .build();
    }

}