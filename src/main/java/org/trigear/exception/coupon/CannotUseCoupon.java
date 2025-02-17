package org.trigear.exception.coupon;

public class CannotUseCoupon extends Exception{
    public CannotUseCoupon() {
        super();
    }

    public CannotUseCoupon(String message) {
        super(message);
    }
}
