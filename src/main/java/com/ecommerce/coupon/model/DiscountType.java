package com.ecommerce.coupon.model;

/**
 * Типы скидок для купонов
 */
public enum DiscountType {
    /**
     * Процентная скидка (например, 10%)
     */
    PERCENTAGE,

    /**
     * Фиксированная сумма скидки (например, 500₽)
     */
    FIXED_AMOUNT,

    /**
     * Бесплатная доставка
     */
    FREE_SHIPPING
}
