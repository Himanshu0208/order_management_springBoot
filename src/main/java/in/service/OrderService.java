package in.service;

import in.dto.orders.OrderResponseDTO;
import in.entity.Orders;
import java.util.List;

public interface OrderService {
    public int createOrder(Orders newOrder);
    public OrderResponseDTO getOrdersById(int id);
    public List<OrderResponseDTO> getAllOrders();
    public boolean deleteOrderById(int id);
}
