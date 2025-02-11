package in.entity;

import jakarta.persistence.*;
import org.hibernate.query.Order;

import java.util.List;

@Entity
public class Coupons {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private byte percentage;
    private double flat;
    private int maxAllowed;
    private int currUsed;

    @OneToMany(mappedBy="coupon")
    private List<Orders> ordersList;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(int maxAllowed) {
        this.maxAllowed = maxAllowed;
    }

    public int getCurrUsed() {
        return currUsed;
    }

    public void setCurrUsed(int currUsed) {
        this.currUsed = currUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getPercentage() {
        return percentage;
    }

    public void setPercentage(byte percentage) {
        this.percentage = percentage;
    }

    public double getFlat() {
        return flat;
    }

    public void setFlat(double flat) {
        this.flat = flat;
    }
}
