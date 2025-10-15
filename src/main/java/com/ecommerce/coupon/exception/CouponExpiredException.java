package com.ecommerce.coupon.exception;

/**
 * Исключение, выбрасываемое когда купон истек
 */
public class CouponExpiredException extends RuntimeException {

    public CouponExpiredException(String message) {
        super(message);
    }
}
