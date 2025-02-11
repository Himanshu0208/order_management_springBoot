package in.controller;

import in.entity.Product;
import in.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getProduct() {
        return productService.getAllProducts();
    }

    @PostMapping("/")
    public Boolean createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProductById(id);
    }
}
