package org.trigear.service;

import org.trigear.dto.orders.OrderDTO;
import org.trigear.dto.product.ProductDTO;

import java.util.List;

public interface OrderDetailsService {
    List<OrderDTO> findOrdersByProduct(int id);
    List<ProductDTO> findProductByOrder(int id);
}
