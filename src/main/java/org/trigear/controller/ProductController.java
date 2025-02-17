package org.trigear.controller;

import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.dto.product.ProductDTO;
import org.trigear.service.OrderDetailsService;
import org.trigear.service.ProductService;
import org.trigear.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");

    private final ProductService productService;
    private final OrderDetailsService orderDetailsService;

    @Autowired
    ProductController(ProductService productService, OrderDetailsService orderDetailsService) {
        this.productService = productService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public List<Product> getProducts() {
        serverLogs.info("get - /product = executing getProducts()");
        List<Product> products = productService.getAllProducts();
        serverLogs.info("get - /product = successfully executed getProducts()");
        return products;
    }

    @PostMapping
    public int createProduct(@RequestBody ProductDTO product) {
        serverLogs.info("post - /product = executing createProduct(ProductDTO product)");
        int id = productService.createProduct(product);
        serverLogs.info("post - /product = successfully executed createProduct(ProductDTO product)");
        return id;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        serverLogs.info("get - /product/{id} = executing getProduct()");
        Product product = productService.getProductById(id);
        serverLogs.info("get - /product/{id} = successfully executed getProduct()");
        return product;
    }

    @GetMapping("/byOrderId/{id}")
    public List<ProductDTO> getProductIdByOrderId(@PathVariable int id) {
        serverLogs.info("get - /product/byOrderId/{id} = executing getProductIdByOrderId(int id)");
        List<ProductDTO> productDTOS = orderDetailsService.findProductByOrder(id);
        serverLogs.info("get - /product/byOrderId/{id} = successfully executed getProductIdByOrderId(int id)");
        return productDTOS;
    }
}
