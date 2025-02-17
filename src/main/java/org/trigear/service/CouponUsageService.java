package org.trigear.service;

import org.springframework.cglib.core.Local;
import org.trigear.entity.Coupons;
import org.trigear.entity.User;

import java.time.LocalDate;

public interface CouponUsageService {
    public boolean canUse(User user, Coupons coupon);
    public int use(User user, Coupons coupon);
    public LocalDate lastUsed(User user, Coupons coupons);
}
