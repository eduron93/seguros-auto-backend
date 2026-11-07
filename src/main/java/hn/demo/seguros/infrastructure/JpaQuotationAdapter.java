package hn.demo.seguros.infrastructure;

import hn.demo.seguros.domain.*;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;


@Repository
public class JpaQuotationAdapter implements QuotationPort {


 private final QuotationRepository repository;


 public JpaQuotationAdapter(
         QuotationRepository repository
 ) {
  this.repository = repository;
 }


 @Override
 public String save(
         Quotation q,
         BigDecimal premium
 ) {

  var entity = new QuotationEntity();

  entity.reference = "COT-"
          + UUID.randomUUID()
          .toString()
          .substring(0, 8)
          .toUpperCase();

  entity.userId = q.userId();
  entity.vehicleYear = q.year();
  entity.brand = q.brand();
  entity.model = q.model();
  entity.vehicleValue = q.vehicleValue();
  entity.productId = q.productId();
  entity.estimatedPremium = premium;


  return repository.save(entity)
          .reference;
 }
}