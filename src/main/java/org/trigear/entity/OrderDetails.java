package org.trigear.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.dto.orders.OrderDetailsDTO;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailsId;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name="orders_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    public OrderDetails(int quantity, double price, Orders order, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
    }
}
