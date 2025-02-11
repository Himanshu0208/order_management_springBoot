package in.service;

import in.dto.coupons.CouponsDTO;
import in.entity.Coupons;

import java.util.List;

public interface CouponsService {
    public int createCoupon(Coupons coupons);
    public CouponsDTO getCouponById(int id);
    public List<CouponsDTO> getAllCoupons();
    public boolean deleteCoupon(int id);
}
