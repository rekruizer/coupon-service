package com.ecommerce.coupon.exception;

/**
 * Исключение, выбрасываемое когда купон невалиден
 */
public class InvalidCouponException extends RuntimeException {

    public InvalidCouponException(String message) {
        super(message);
    }
}
