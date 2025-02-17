package org.trigear.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.dto.coupons.CouponsDTO;
import org.trigear.exception.coupon.CouponNotFound;
import org.trigear.repository.CouponRepository;
import org.trigear.entity.Coupons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CouponsServiceImplementation implements CouponsService {

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");
    private final CouponRepository couponRepository;

    @Autowired
    CouponsServiceImplementation(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public int createCoupon(CouponsDTO newCoupon) {
        serverLogs.trace("running createCoupon");
        try {
            serverLogs.trace("creating coupon object");
            Coupons coupon = new Coupons(newCoupon);
            serverLogs.debug("saving coupon object into db");
            couponRepository.save(coupon);
            serverLogs.trace("running createCoupon completed");
            return coupon.getId();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }

    @Override
    public CouponsDTO getCouponById(int id) {
        serverLogs.trace("running getCouponById");
        try {
            serverLogs.debug("fetching coupon from db");
            Coupons coupon = couponRepository.findById(id).orElse(null);
            if(coupon == null) {
                throw new CouponNotFound("Coupon not found: id-"+id);
            }
            serverLogs.trace("running getCouponById completed");
            return new CouponsDTO(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return null;
        }

    }

    @Override
    public List<CouponsDTO> getAllCoupons() {
        serverLogs.trace("running getAllCoupons");

        try {
            serverLogs.debug("fetching all coupons");
            List<Coupons> coupons = couponRepository.findAll();

            List<CouponsDTO> couponsDTOS = new ArrayList<>();
            serverLogs.trace("Converting the fetched coupons into CouponsDTO");
            for(Coupons coupon: coupons) {
                couponsDTOS.add(new CouponsDTO(coupon));
            }
            serverLogs.trace("running getAllCoupons completed");
            return couponsDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

    @Override
    public boolean deleteCoupon(int id) {
        serverLogs.trace("running deleteCoupon");
        try {
            serverLogs.debug("checking that coupon with given id#{}",id);
            CouponsDTO coupon = getCouponById(id);
            if(coupon == null) {
                throw new CouponNotFound("Coupon not found: id-"+id);
            }
            serverLogs.trace("Coupon with given id#{} exists",id);
            serverLogs.debug("deleting coupon with id#{}",id);
            couponRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }
}
