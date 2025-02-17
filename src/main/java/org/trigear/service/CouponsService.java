package org.trigear.service;

import org.trigear.dto.coupons.CouponsDTO;
import org.trigear.entity.Coupons;

import java.util.List;

public interface CouponsService {
    public int createCoupon(CouponsDTO coupons);
    public CouponsDTO getCouponById(int id);
    public List<CouponsDTO> getAllCoupons();
    public boolean deleteCoupon(int id);
}
