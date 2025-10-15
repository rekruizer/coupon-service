package com.ecommerce.coupon.exception;

/**
 * Исключение, выбрасываемое когда купон не найден
 */
public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(String message) {
        super(message);
    }

    public CouponNotFoundException(String code, String message) {
        super(String.format("Купон с кодом '%s' не найден: %s", code, message));
    }
}
