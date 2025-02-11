package in.service;

import in.dto.coupons.CouponsDTO;
import in.entity.Coupons;
import in.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponsServiceImplementation implements CouponsService {

    private final CouponRepository couponRepository;

    @Autowired
    CouponsServiceImplementation(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public int createCoupon(Coupons newCoupons) {
        try {
            couponRepository.save(newCoupons);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return newCoupons.getId();
    }

    @Override
    public CouponsDTO getCouponById(int id) {
        Coupons coupon = couponRepository.findById(id).orElse(null);
        if(coupon == null) return null;
        return new CouponsDTO(coupon);
    }

    @Override
    public List<CouponsDTO> getAllCoupons() {
        List<Coupons> coupons = couponRepository.findAll();
        List<CouponsDTO> couponsDTOS = new ArrayList<>();
        for(Coupons coupon: coupons) {
            couponsDTOS.add(new CouponsDTO(coupon));
        }
        return couponsDTOS;
    }

    @Override
    public boolean deleteCoupon(int id) {
        try {
            couponRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
