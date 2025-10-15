package com.ecommerce.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO для результата валидации купона
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationResponse {

    private Boolean success;
    private String message;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal finalPrice;
}
