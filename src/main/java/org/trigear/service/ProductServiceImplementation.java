package org.trigear.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.dto.product.ProductDTO;
import org.trigear.entity.Product;
import org.trigear.exception.ProductNotFound;
import org.trigear.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService{

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");

    private final ProductRepository productRepository;

    ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public int createProduct(ProductDTO newProduct) {
        serverLogs.trace("Executing createProduct");
        try {
            serverLogs.trace("Creating product");
            Product product = new Product(newProduct);

            serverLogs.debug("Saving product to DB");
            productRepository.save(product);
            serverLogs.debug("Saving product to DB: Completed");

            serverLogs.trace("Executing createProduct completed");
            return product.getProductId();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }

    @Override
    public Product getProductById(int id) {
        serverLogs.trace("Executing getProductById");
        try {
            serverLogs.debug("Fetching product with id#{}",id);
            Product product = productRepository.findById(id).orElse(null);
            if(product == null) {
                throw new ProductNotFound("No product with id#"+id+" found in DB");
            }

            serverLogs.trace("Executing getProductById completed");
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        serverLogs.trace("Executing getAllProducts");
        try {
            serverLogs.debug("Fetching all products");
            List<Product> products = productRepository.findAll();
            serverLogs.debug("Fetching all products: Completed");

            serverLogs.trace("Executing getAllProducts completed");
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }
}
