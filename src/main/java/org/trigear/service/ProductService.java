package org.trigear.service;

import org.trigear.dto.product.ProductDTO;
import org.trigear.entity.Product;

import java.util.List;

public interface ProductService {
    public int createProduct(ProductDTO newProduct);
    public Product getProductById(int id);
    public List<Product> getAllProducts();
}
