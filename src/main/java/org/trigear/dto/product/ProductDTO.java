package org.trigear.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.entity.Product;

@Setter
@Getter
@NoArgsConstructor
public class ProductDTO {
    private int productId;
    private String name;
    private String description;
    private double price;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.description = product.getDescription();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
