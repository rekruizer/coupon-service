package com.ecommerce.coupon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity класс для купона
 */
@Entity
@Table(name = "coupons", indexes = {
    @Index(name = "idx_code", columnList = "code", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальный код купона (например, "SALE10ABC")
     */
    @Column(nullable = false, unique = true, length = 20)
    private String code;

    /**
     * Тип скидки
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    /**
     * Значение скидки (10 для 10%, или 500 для 500₽)
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    /**
     * Минимальная сумма заказа для применения купона
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minOrderAmount;

    /**
     * Дата окончания действия купона
     */
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    /**
     * Максимальное количество использований
     */
    @Column(nullable = false)
    private Integer usageLimit;

    /**
     * Текущее количество использований
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer usageCount = 0;

    /**
     * Активен ли купон
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    /**
     * Дата создания купона
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (usageCount == null) {
            usageCount = 0;
        }
        if (active == null) {
            active = true;
        }
    }
}
