package org.trigear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trigear.entity.OrderDetails;
import org.trigear.entity.Orders;
import org.trigear.entity.Product;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findByOrder(Orders order);
    List<OrderDetails> findByProduct(Product product);
}
