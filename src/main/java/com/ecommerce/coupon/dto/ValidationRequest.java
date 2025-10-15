package com.ecommerce.coupon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO для валидации купона
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationRequest {

    @NotNull(message = "Сумма заказа обязательна")
    @DecimalMin(value = "0.01", message = "Сумма заказа должна быть больше 0")
    private BigDecimal orderAmount;
}
