package hn.demo.seguros.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;


@Entity
@Table(name = "quotations")
public class QuotationEntity {


 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public Long id;


 @Column(
         unique = true,
         nullable = false
 )
 public String reference;


 public Long userId;

 public int vehicleYear;

 public String brand;

 public String model;

 public BigDecimal vehicleValue;

 public Long productId;

 public BigDecimal estimatedPremium;


 protected QuotationEntity() {
 }
}