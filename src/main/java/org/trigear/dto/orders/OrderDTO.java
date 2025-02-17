package org.trigear.dto.orders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.dto.coupons.CouponUsageDTO;
import org.trigear.entity.OrderDetails;
import org.trigear.entity.Orders;
import org.trigear.entity.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private double amount;
    private int userId;
    private Date orderedAt;
    private CouponUsageDTO coupon;
    private List<OrderDetailsDTO> orderDetails = new ArrayList<>();

    public OrderDTO(Orders orders) {
        this.orderId = orders.getOrdersId();
        this.amount = orders.getAmount();
        this.userId = orders.getUser().getUserId();
        this.coupon = orders.getCoupon() != null ? new CouponUsageDTO(orders.getCoupon()) : null;
        this.orderedAt = orders.getOrderedAt();
        for(OrderDetails orderDetails: orders.getOrderDetailsList()) {
            this.orderDetails.add(new OrderDetailsDTO(orderDetails));
        }
    }
}
