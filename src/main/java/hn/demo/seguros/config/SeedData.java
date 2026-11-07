package hn.demo.seguros.config;

import hn.demo.seguros.infrastructure.ProductRepository;
import hn.demo.seguros.infrastructure.ProductEntity;
import hn.demo.seguros.infrastructure.UserRepository;
import hn.demo.seguros.infrastructure.UserEntity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;


@Configuration
public class SeedData {


 @Bean
 CommandLineRunner seed(
         UserRepository users,
         ProductRepository products,
         PasswordEncoder encoder,
         @Value("${app.demo-password}") String password
 ) {

  return args -> {


   if (users.count() == 0) {

    users.save(
            new UserEntity(
                    "demo",
                    encoder.encode(password)
            )
    );
   }


   if (products.count() == 0) {

    products.save(
            new ProductEntity(
                    "Responsabilidad Civil",
                    "Daños a terceros",
                    "Sujeto a evaluación",
                    new BigDecimal("1000"),
                    new BigDecimal("10000"),
                    new BigDecimal("0.025")
            )
    );


    products.save(
            new ProductEntity(
                    "Cobertura Básica",
                    "Terceros y robo",
                    "Sujeto a evaluación",
                    new BigDecimal("2000"),
                    new BigDecimal("20000"),
                    new BigDecimal("0.04")
            )
    );


    products.save(
            new ProductEntity(
                    "Cobertura Completa",
                    "Cobertura amplia",
                    "Sujeto a evaluación",
                    new BigDecimal("4000"),
                    new BigDecimal("40000"),
                    new BigDecimal("0.06")
            )
    );
   }
  };
 }
}