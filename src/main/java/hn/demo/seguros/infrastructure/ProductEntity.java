package hn.demo.seguros.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;


@Entity
@Table(name = "products")
public class ProductEntity {


 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public Long id;


 public String name;

 public String description;

 public String conditions;

 public BigDecimal minPrice;

 public BigDecimal maxPrice;

 public BigDecimal rate;

 public boolean active = true;


 protected ProductEntity() {
 }


 public ProductEntity(
         String name,
         String description,
         String conditions,
         BigDecimal minPrice,
         BigDecimal maxPrice,
         BigDecimal rate
 ) {

  this.name = name;
  this.description = description;
  this.conditions = conditions;
  this.minPrice = minPrice;
  this.maxPrice = maxPrice;
  this.rate = rate;
 }
}
