package org.trigear.repository;

import org.trigear.entity.CouponUsage;
import org.trigear.entity.Coupons;
import org.trigear.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Integer> {
    CouponUsage findByUserAndCoupon(User user, Coupons coupon);
}
