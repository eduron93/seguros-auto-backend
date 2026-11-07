package hn.demo.seguros.application;

import hn.demo.seguros.domain.*;
import hn.demo.seguros.infrastructure.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;


@Service
public class QuotationService {


 private final QuotationPort quotations;
 private final ProductRepository products;


 public QuotationService(
         QuotationPort quotations,
         ProductRepository products
 ) {
  this.quotations = quotations;
  this.products = products;
 }


 public Result create(
         Quotation q
 ) {

  var product = products.findById(
          q.productId()
  ).orElseThrow(
          () -> new ResponseStatusException(
                  HttpStatus.BAD_REQUEST,
                  "Producto inexistente"
          )
  );


  if (!product.active) {

   throw new ResponseStatusException(
           HttpStatus.BAD_REQUEST,
           "Producto inactivo"
   );
  }


  validateVehicle(q);


  BigDecimal premium = q.vehicleValue()
          .multiply(product.rate)
          .setScale(
                  2,
                  RoundingMode.HALF_UP
          );


  return new Result(
          quotations.save(q, premium),
          premium
  );
 }


 private void validateVehicle(
         Quotation q
 ) {

  int currentYear = Year.now()
          .getValue();


  if (q.vehicleValue() == null
          || q.vehicleValue().signum() <= 0
          || q.year() < 1980
          || q.year() > currentYear + 1
          || q.brand() == null
          || q.brand().isBlank()
          || q.model() == null
          || q.model().isBlank()) {


   throw new ResponseStatusException(
           HttpStatus.BAD_REQUEST,
           "Datos de vehículo inválidos"
   );
  }
 }


 public record Result(
         String reference,
         BigDecimal estimatedPremium
 ) {}
}