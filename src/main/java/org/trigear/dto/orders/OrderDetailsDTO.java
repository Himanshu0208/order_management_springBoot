package org.trigear.dto.orders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.entity.OrderDetails;
import org.trigear.entity.Product;

@Setter
@Getter
@NoArgsConstructor
public class OrderDetailsDTO {
    private double price;
    private int quantity;
    private int productId;
    private String productName;


    public OrderDetailsDTO(OrderDetails orderDetails) {
        Product product = orderDetails.getProduct();

        this.price = orderDetails.getPrice();
        this.quantity = orderDetails.getQuantity();
        this.productId = product.getProductId();
        this.productName = product.getName();
    }
}