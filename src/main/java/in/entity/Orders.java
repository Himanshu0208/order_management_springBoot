package in.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Orders {

    @JoinTable()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ordersId;
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="coupon_id")
    private Coupons coupon;

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Coupons getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupons coupon) {
        this.coupon = coupon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int id) {
        this.ordersId = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}