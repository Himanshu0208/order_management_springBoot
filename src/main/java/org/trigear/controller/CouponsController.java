package org.trigear.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.dto.coupons.CouponsDTO;
import org.trigear.service.CouponsService;
import org.trigear.entity.Coupons;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");
    private final CouponsService couponsService;

    CouponsController(CouponsService couponsService) {
        this.couponsService = couponsService;
    }

    @PostMapping
    public int createCoupons(@RequestBody CouponsDTO coupon) {
        serverLogs.info("post - /coupons = executing createCoupons(CouponsDTO coupon)");
        int id = couponsService.createCoupon(coupon);
        serverLogs.info("post - /coupons = successfully executed createCoupons(CouponsDTO coupon)");
        return id;
    }

    @GetMapping
    public List<CouponsDTO> getCoupons() {
        serverLogs.info("get - /coupons = executing getCoupons()");
        List<CouponsDTO> coupons = couponsService.getAllCoupons();
        serverLogs.info("get - /coupons = successfully executed getCoupons()");
        return coupons;
    }

    @GetMapping("/{id}")
    public CouponsDTO getCoupon(@PathVariable("id") int id) {
        serverLogs.info("get - /coupons/{id} = executing getCoupon(int id)");
        CouponsDTO coupon = couponsService.getCouponById(id);
        serverLogs.info("get - /coupons/{id} = successfully executed getCoupons(int id)");
        return coupon;
    }

    @DeleteMapping("/{id}")
    public boolean deleteCoupons(@PathVariable("id") int id) {
        serverLogs.info("delete - /coupons/{id} = executing deleteCoupons(int id)");
        boolean isDeleted = couponsService.deleteCoupon(id);
        serverLogs.info("delete - /coupons/{id} = successfully executed deleteCoupons(int id)");
        return isDeleted;
    }
}
