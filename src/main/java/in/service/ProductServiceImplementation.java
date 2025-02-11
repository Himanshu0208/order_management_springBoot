package in.service;

import in.entity.Product;
import in.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private final ProductRepository productRepository;

    ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean createProduct(Product product) {
        try {
            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepository.findById(id).orElse(null);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }
}
