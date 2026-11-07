package hn.demo.seguros.api;

import hn.demo.seguros.infrastructure.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


@RestController
public class AuthController {


 private final UserRepository users;
 private final PasswordEncoder passwords;
 private final JwtEncoder encoder;


 public AuthController(
         UserRepository users,
         PasswordEncoder passwords,
         JwtEncoder encoder
 ) {
  this.users = users;
  this.passwords = passwords;
  this.encoder = encoder;
 }


 public record Login(
         String username,
         String password
 ) {}


 public record AuthResult(
         boolean valid,
         String token,
         Long userId
 ) {}


 @PostMapping("/auth/verify")
 public ResponseEntity<AuthResult> verify(
         @RequestBody Login request
 ) {

  var username = request.username() == null
          ? ""
          : request.username();


  var user = users.findByUsername(username);


  if (user.isEmpty()
          || request.password() == null
          || !passwords.matches(
          request.password(),
          user.get().passwordHash
  )) {

   return ResponseEntity
           .status(401)
           .body(
                   new AuthResult(
                           false,
                           null,
                           null
                   )
           );
  }


  var now = Instant.now();


  var claims = JwtClaimsSet.builder()
          .issuer("seguros-auto")
          .subject(user.get().id.toString())
          .issuedAt(now)
          .expiresAt(
                  now.plusSeconds(3600)
          )
          .build();


  var token = encoder.encode(
          JwtEncoderParameters.from(
                  JwsHeader.with(
                          MacAlgorithm.HS256
                  ).build(),
                  claims
          )
  ).getTokenValue();


  return ResponseEntity.ok(
          new AuthResult(
                  true,
                  token,
                  user.get().id
          )
  );
 }
}
