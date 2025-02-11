package in.entity;

import jakarta.persistence.*;

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

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
