package in.controller;

import in.dto.coupons.CouponsDTO;
import in.entity.Coupons;
import in.service.CouponsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private final CouponsService couponsService;

    CouponsController(CouponsService couponsService) {
        this.couponsService = couponsService;
    }

    @PostMapping("/")
    public int createCoupons(@RequestBody Coupons coupon) {
        return couponsService.createCoupon(coupon);
    }

    @GetMapping("/")
    public List<CouponsDTO> getCoupons() {
        return couponsService.getAllCoupons();
    }

    @GetMapping("/{id}")
    public CouponsDTO getCoupon(@PathVariable("id") int id) {
        return couponsService.getCouponById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCoupons(@PathVariable("id") int id) {
        return couponsService.deleteCoupon(id);
    }
}
