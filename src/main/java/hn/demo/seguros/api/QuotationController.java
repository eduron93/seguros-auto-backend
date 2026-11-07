package hn.demo.seguros.api;

import hn.demo.seguros.application.QuotationService;
import hn.demo.seguros.domain.Quotation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
public class QuotationController {


 private final QuotationService service;


 public QuotationController(
         QuotationService service
 ) {
  this.service = service;
 }


 public record Request(
         int year,
         String brand,
         String model,
         BigDecimal vehicleValue,
         Long productId,
         Long userId
 ) {}


 @PostMapping("/quotations")
 public ResponseEntity<QuotationService.Result> create(
         @RequestBody Request request,
         JwtAuthenticationToken auth
 ) {

  Long authenticatedId = Long.valueOf(
          auth.getToken()
                  .getSubject()
  );


  if (request.userId() != null
          && !request.userId().equals(authenticatedId)) {

   return ResponseEntity
           .status(403)
           .build();
  }


  Quotation quotation = new Quotation(
          authenticatedId,
          request.year(),
          request.brand(),
          request.model(),
          request.vehicleValue(),
          request.productId()
  );


  return ResponseEntity
          .status(201)
          .body(
                  service.create(quotation)
          );
 }
}