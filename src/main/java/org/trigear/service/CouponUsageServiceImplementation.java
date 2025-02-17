package org.trigear.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.entity.*;
import org.trigear.repository.CouponUsageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class CouponUsageServiceImplementation implements CouponUsageService{

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");
    private final CouponUsageRepository couponUsageRepository;

    public CouponUsageServiceImplementation(CouponUsageRepository couponUsageRepository) {
        this.couponUsageRepository = couponUsageRepository;
    }

    @Override
    public boolean canUse(User user, Coupons coupon) {
        serverLogs.trace("executing canUse");
        try {
            serverLogs.debug("Fetching couponUsage details for given user and coupon");
            CouponUsage couponUsage = couponUsageRepository.findByUserAndCoupon(user, coupon);

            boolean ans = false;
            if(couponUsage == null) {
                serverLogs.trace("no record for the given user and coupon found");
                couponUsage = new CouponUsage();
                couponUsage.setCoupon(coupon);
                couponUsage.setUser(user);
                couponUsage.setLastUsage(LocalDate.now());

                ans = true;
            }
            else {
                serverLogs.trace("found a couponUsage record for the given user and coupon");
                LocalDate currDate = LocalDate.now();
                LocalDate lastUsage = couponUsage.getLastUsage();

                String usage = coupon.getUsageFrequency();
                int useAfter = coupon.getUsageFrequencyAmount();

                switch (usage) {
                    case "unlimited":
                        ans = true;
                        break;
                    case "once":
                        ans = false;
                        break;
                    case "daily":
                        ans = lastUsage.plusDays(useAfter).isBefore(currDate);
                        break;
                    case "weekly":
                        ans = lastUsage.plusWeeks(useAfter).isBefore(currDate);
                        break;
                    case "monthly":
                        ans = lastUsage.plusMonths(useAfter).isBefore(currDate);
                        break;
                    case "yearly":
                        ans = lastUsage.plusYears(useAfter).isBefore(currDate);
                    break;
                }
            }

            if(ans == true) {
                couponUsage.setLastUsage(LocalDate.now());
            }

            serverLogs.trace("executing canUse completed");
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public int use(User user, Coupons coupon) {
        serverLogs.trace("executing use completed");
        try {
            serverLogs.trace("Creating couponUsage object");
            CouponUsage couponUsage = new CouponUsage();
            couponUsage.setCoupon(coupon);
            couponUsage.setUser(user);
            couponUsage.setLastUsage(LocalDate.now());

            serverLogs.trace("saving couponUsage object into db");
            couponUsageRepository.save(couponUsage);
            serverLogs.trace("executing use completed");
            return couponUsage.getId();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }

    @Override
    public LocalDate lastUsed(User user, Coupons coupons) {
        serverLogs.trace("executing lastUsed");
        try {
            serverLogs.debug("searching for couponUsage for given user and coupon");
            CouponUsage couponUsage = couponUsageRepository.findByUserAndCoupon(user, coupons);
            if(couponUsage == null) {
                serverLogs.trace("No couponUsage for given user and coupon found");
                return null;
            }
            serverLogs.trace("executing lastUsed completed");
            return couponUsage.getLastUsage();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
