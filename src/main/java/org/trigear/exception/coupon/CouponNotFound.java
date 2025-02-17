package org.trigear.exception.coupon;

public class CouponNotFound extends Exception{
    public CouponNotFound() {
        super();
    }

    public CouponNotFound(String message) {
        super(message);
    }
}
