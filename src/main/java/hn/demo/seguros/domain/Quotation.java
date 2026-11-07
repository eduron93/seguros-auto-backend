package hn.demo.seguros.domain;

import java.math.BigDecimal;


public record Quotation(
        Long userId,
        int year,
        String brand,
        String model,
        BigDecimal vehicleValue,
        Long productId
) {
}