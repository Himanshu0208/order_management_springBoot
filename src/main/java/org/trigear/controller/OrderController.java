package org.trigear.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.dto.orders.OrderDTO;
import org.trigear.service.OrderDetailsService;
import org.trigear.service.OrderService;
import org.trigear.service.UserService;
import org.trigear.entity.Orders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");

    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;

    OrderController(OrderService orderService, OrderDetailsService orderDetailsService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public List<OrderDTO> getOrders() {
        serverLogs.info("get - /orders = executing getOrders()");
        List<OrderDTO> orders = orderService.getAllOrders();
        serverLogs.info("get - /orders = successfully executed getOrders()");
        return orders;
    }

    @GetMapping("/{order_id}")
    public OrderDTO getOrder(@PathVariable("order_id") int id) {
        serverLogs.info("get - /orders/{order_id} = executing getOrder(int id)");
        OrderDTO order = orderService.getOrdersById(id);
        serverLogs.info("get - /orders/{order_id} = successfully executed getOrder(int id)");
        return order;
    }

    @PostMapping
    public int createOrder(@RequestBody OrderDTO orders) {
        serverLogs.info("post - /orders = executing createOrder(OrderDTO orders)");
        int id = orderService.createOrder(orders);
        serverLogs.info("post - /orders = successfully executed createOrder(OrderDTO orders)");
        return id;
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrderById(@PathVariable int id) {
        serverLogs.info("delete - /orders/{id} = executing deleteOrderById(int id)");
        boolean isDeleted = orderService.deleteOrderById(id);
        serverLogs.info("delete - /orders/{id} = executing deleteOrderById(int id)");
        return isDeleted;
    }

    @GetMapping("/byProductId/{id}")
    public List<OrderDTO> getOrdersIdByProductId(@PathVariable int id) {
        serverLogs.info("get - /orders/byProductId/{id} = executing getOrdersIdByProductId(int id)");
        List<OrderDTO> orders = orderDetailsService.findOrdersByProduct(id);
        serverLogs.info("get - /orders/byProductId/{id} = executing getOrdersIdByProductId(int id)");
        return orders;
    }

}
