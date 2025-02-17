package org.trigear.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.dto.coupons.CouponsDTO;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Coupons {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private byte percentage;
    private double flat;
    private int maxAllowed;
    private int currUsed;
    private String usageFrequency;
    private int usageFrequencyAmount;

    @OneToMany(mappedBy="coupon")
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "coupon")
    private List<CouponUsage> couponUsage;

    public Coupons(CouponsDTO couponsDTO) {
        this.id = couponsDTO.getId();
        this.name = couponsDTO.getName();
        this.percentage = couponsDTO.getPercentage();
        this.flat = couponsDTO.getFlat();
        this.maxAllowed = couponsDTO.getMaxAllowed();
        this.usageFrequency = couponsDTO.getUsageFrequency();
        this.usageFrequencyAmount = couponsDTO.getUsageFrequencyAmount();
    }
}
