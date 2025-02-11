package in.service;

import in.entity.Product;

import java.util.List;

public interface ProductService {
    public boolean createProduct(Product product);
    public Product getProductById(int id);
    public List<Product> getAllProducts();
}
