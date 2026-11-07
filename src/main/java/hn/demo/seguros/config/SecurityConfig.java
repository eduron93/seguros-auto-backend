package hn.demo.seguros.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Configuration
public class SecurityConfig {


 @Bean
 PasswordEncoder passwordEncoder() {

  return new BCryptPasswordEncoder();
 }


 @Bean
 JwtEncoder jwtEncoder(
         @Value("${app.jwt-secret}") String secret
 ) {

  return new NimbusJwtEncoder(
          new ImmutableSecret<>(
                  secret.getBytes(StandardCharsets.UTF_8)
          )
  );
 }


 @Bean
 JwtDecoder jwtDecoder(
         @Value("${app.jwt-secret}") String secret
 ) {

  return NimbusJwtDecoder
          .withSecretKey(
                  new SecretKeySpec(
                          secret.getBytes(StandardCharsets.UTF_8),
                          "HmacSHA256"
                  )
          )
          .macAlgorithm(
                  MacAlgorithm.HS256
          )
          .build();
 }


 @Bean
 SecurityFilterChain security(
         HttpSecurity http
 ) throws Exception {


  return http

          .csrf(csrf -> csrf.disable())

          .cors(cors -> {})

          .sessionManagement(session ->
                  session.sessionCreationPolicy(
                          SessionCreationPolicy.STATELESS
                  )
          )

          .authorizeHttpRequests(auth -> auth

                  .requestMatchers(
                          "/auth/verify",
                          "/actuator/health"
                  )
                  .permitAll()

                  .anyRequest()
                  .authenticated()
          )

          .oauth2ResourceServer(
                  oauth2 -> oauth2.jwt(jwt -> {})
          )

          .build();
 }


 @Bean
 CorsConfigurationSource corsConfigurationSource(
         @Value("${app.frontend-origin}") String origin
 ) {

  var configuration = new CorsConfiguration();

  configuration.setAllowedOrigins(
          List.of(origin)
  );

  configuration.setAllowedMethods(
          List.of(
                  "GET",
                  "POST",
                  "OPTIONS"
          )
  );

  configuration.setAllowedHeaders(
          List.of(
                  "Authorization",
                  "Content-Type"
          )
  );


  var source = new UrlBasedCorsConfigurationSource();

  source.registerCorsConfiguration(
          "/**",
          configuration
  );


  return source;
 }
}