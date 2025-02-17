package org.trigear.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.trigear.dto.orders.OrderDTO;
import org.trigear.dto.product.ProductDTO;
import org.trigear.entity.OrderDetails;
import org.trigear.entity.Orders;
import org.trigear.entity.Product;
import org.trigear.exception.order.OrderNotFound;
import org.trigear.exception.ProductNotFound;
import org.trigear.repository.OrderDetailsRepository;
import org.trigear.repository.OrderRepository;
import org.trigear.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderDetailsServiceImplementation implements OrderDetailsService{

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");

    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderDetailsServiceImplementation(
            OrderDetailsRepository orderDetailsRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDTO> findOrdersByProduct(int id) {
        serverLogs.trace("Executing findOrdersByProduct");
        try {
            List<OrderDTO> orderIds = new ArrayList<>();

            serverLogs.debug("fetching product with id#{}",id);
            Product foundProduct = productRepository.findById(id).orElse(null);
            if(foundProduct == null) {
                throw new ProductNotFound("No product with given id exists");
            }

            serverLogs.debug("fetching product with id#{}",id);
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findByProduct(foundProduct);

            serverLogs.trace("Converting the orderDetails into orderDetailsDTO");
            for(OrderDetails orderDetails: orderDetailsList) {
                orderIds.add(new OrderDTO(orderDetails.getOrder()));
            }
            serverLogs.trace("Executing findOrdersByProduct completed");
            return orderIds;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return List.of();
        }

    }

    @Override
    public List<ProductDTO> findProductByOrder(int id) {
        serverLogs.trace("Executing findProductByOrder");
        try {
            List<ProductDTO> productIds = new ArrayList<>();

            serverLogs.debug("Fetching order by id#{}",id);
            Orders foundOrder = orderRepository.findById(id).orElse(null);
            if(foundOrder == null) {
                throw new OrderNotFound("Order Not found");
            }
            serverLogs.trace("Order by id#{} found",id);

            serverLogs.debug("Fetching orderDetails by order");
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrder(foundOrder);

            serverLogs.trace("Converting the orderDetails into orderDetailsDTO");
            for(OrderDetails orderDetails: orderDetailsList) {
                productIds.add(new ProductDTO(orderDetails.getProduct()));
            }

            serverLogs.trace("Executing findProductByOrder completed");
            return productIds;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return List.of();
        }

    }
}
