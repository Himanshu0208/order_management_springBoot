package in.controller;

import in.dto.orders.OrderResponseDTO;
import in.entity.OrderDetails;
import in.entity.Orders;
import in.entity.User;
import in.service.OrderService;
import in.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<OrderResponseDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{order_id}")
    public OrderResponseDTO getOrder(@PathVariable("order_id") int id) {
        return orderService.getOrdersById(id);
    }

    @PostMapping("/")
    public int createOrder(@RequestBody Orders orders) {
        return orderService.createOrder(orders);
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrderById(@PathVariable int id) {
        return orderService.deleteOrderById(id);
    }

}
