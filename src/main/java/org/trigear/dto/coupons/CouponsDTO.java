package org.trigear.dto.coupons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.entity.Coupons;

@Setter
@Getter
@NoArgsConstructor
public class CouponsDTO {
    private int id;
    private String name;
    private int maxAllowed;
    private byte percentage;
    private int currUsed;
    private double flat;
    private String usageFrequency;
    private int usageFrequencyAmount;

    public CouponsDTO(Coupons coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.maxAllowed = coupon.getMaxAllowed();
        this.percentage = coupon.getPercentage();
        this.flat = coupon.getFlat();
        this.currUsed = coupon.getCurrUsed();
        this.usageFrequency = coupon.getUsageFrequency();
        this.usageFrequencyAmount = coupon.getUsageFrequencyAmount();
    }
}
