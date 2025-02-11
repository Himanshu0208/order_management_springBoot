package in.dto.orders;

import in.dto.coupons.CouponsDTO;
import in.entity.OrderDetails;
import in.entity.Orders;
import in.entity.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderResponseDTO {
    private int orderId;
    private double amount;
    private int userId;
    private Date orderedAt;
    private CouponsDTO coupon;
    private List<OrderDetailsDTO> orderDetails = new ArrayList<>();

    public OrderResponseDTO(Orders orders) {
        this.orderId = orders.getOrdersId();
        this.amount = orders.getAmount();
        this.userId = orders.getUser().getUserId();
        this.coupon = new CouponsDTO(orders.getCoupon());
        this.orderedAt = orders.getOrderedAt();
        for(OrderDetails orderDetails: orders.getOrderDetailsList()) {
            this.orderDetails.add(new OrderDetailsDTO(orderDetails));
        }
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public CouponsDTO getCoupon() {
        return coupon;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public int getUserId() {
        return userId;
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }
}

class OrderDetailsDTO {
    private double price;
    private int quantity;
    private int productId;
    private String productName;


    OrderDetailsDTO(OrderDetails orderDetails) {
        Product product = orderDetails.getProduct();

        this.price = orderDetails.getPrice();
        this.quantity = orderDetails.getQuantity();
        this.productId = product.getProductId();
        this.productName = product.getName();
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
