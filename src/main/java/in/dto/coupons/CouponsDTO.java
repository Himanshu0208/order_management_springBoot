package in.dto.coupons;

import in.entity.Coupons;

public class CouponsDTO {
    private int id;
    private String name;
    private int maxAllowed;
    private byte percentage;
    private int currUsed;
    private double flat;

    public CouponsDTO(Coupons coupon) {
        if(coupon != null) {
            this.id = coupon.getId();
            this.name = coupon.getName();
            this.maxAllowed = coupon.getMaxAllowed();
            this.percentage = coupon.getPercentage();
            this.flat = coupon.getFlat();
            this.currUsed = coupon.getCurrUsed();
        }
        else {
            this.id = -1;
            this.name = "";
            this.maxAllowed = 0;
            this.percentage = 0;
            this.flat = 0;
            this.currUsed = 0;
        }
    }

    public int getCurrUsed() {
        return currUsed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public int getPercentage() {
        return percentage;
    }

    public double getFlat() {
        return flat;
    }
}
