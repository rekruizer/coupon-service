package com.ecommerce.coupon.dto;

import com.ecommerce.coupon.model.DiscountType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для создания купона
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponCreateRequest {

    @NotNull(message = "Тип скидки обязателен")
    private DiscountType discountType;

    @NotNull(message = "Значение скидки обязательно")
    @DecimalMin(value = "0.01", message = "Значение скидки должно быть больше 0")
    private BigDecimal discountValue;

    @NotNull(message = "Минимальная сумма заказа обязательна")
    @DecimalMin(value = "0.00", message = "Минимальная сумма заказа не может быть отрицательной")
    private BigDecimal minOrderAmount;

    @NotNull(message = "Дата окончания обязательна")
    @Future(message = "Дата окончания должна быть в будущем")
    private LocalDateTime expiryDate;

    @NotNull(message = "Лимит использований обязателен")
    @Min(value = 1, message = "Лимит использований должен быть больше 0")
    private Integer usageLimit;
}
