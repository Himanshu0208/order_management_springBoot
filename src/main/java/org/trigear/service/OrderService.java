package org.trigear.service;

import org.trigear.dto.orders.OrderDTO;
import org.trigear.entity.Orders;

import java.util.List;

public interface OrderService {
    public int createOrder(OrderDTO newOrder);
    public OrderDTO getOrdersById(int id);
    public List<OrderDTO> getAllOrders();
    public boolean deleteOrderById(int id);
}
