package org.trigear.dto.coupons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.entity.Coupons;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CouponUsageDTO {
    private int couponId;
    private String couponName;
    private float percentage;
    private double flat;

    public CouponUsageDTO(Coupons coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getName();
        this.percentage = coupon.getPercentage();
        this.flat = coupon.getFlat();
    }
}
