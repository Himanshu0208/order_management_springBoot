package in.service;

import in.dto.orders.OrderResponseDTO;
import in.entity.Coupons;
import in.entity.OrderDetails;
import in.entity.Orders;
import in.entity.Product;
import in.repository.CouponRepository;
import in.repository.OrderRepository;
import in.repository.ProductRepository;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    @Autowired
    OrderServiceImplementation(OrderRepository orderRepository,
                               ProductRepository productRepository,
                               CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.couponRepository = couponRepository;
    }

    @Override
    public int createOrder(Orders newOrder) {
        try {
            double amount = 0;
            double discount = 0;

            int appliedCouponId = newOrder.getCoupon().getId();

            Coupons coupon = couponRepository.findById(appliedCouponId).orElse(null);

            float percentage = coupon != null ? coupon.getPercentage() : 1;
            double flat = coupon != null ?  coupon.getFlat() : 0;

            for(OrderDetails orderDetails: newOrder.getOrderDetailsList()) {
                int orderedProductId = orderDetails.getProduct().getProductId();
                Product product = productRepository.findById(orderedProductId).orElse(null);

                if(product == null) {
                    throw new Exception("Can't find the product with id "+orderedProductId);
                }

                orderDetails.setPrice(product.getPrice());
                amount += (orderDetails.getPrice()*orderDetails.getQuantity());
                orderDetails.setOrder(newOrder);
            }

            int maxAllowed = coupon != null ? coupon.getMaxAllowed() : 0;
            int currUsed = coupon != null ? coupon.getCurrUsed() : 0;
            if(maxAllowed == -1 || currUsed < maxAllowed) {
                discount = Math.min(amount*percentage/100, flat);
                System.out.println(amount+" "+discount+" - "+amount*percentage/100+" , "+flat);

                coupon.setCurrUsed(currUsed+1);
                couponRepository.save(coupon);
            }
            else {
                discount = 0;
            }

            newOrder.setAmount(amount-discount);
            newOrder.setOrderedAt(new Date());
            orderRepository.save(newOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return newOrder.getOrdersId();
    }

    @Override
    public OrderResponseDTO getOrdersById(int id) {
        Orders order = orderRepository.findById(id).orElse(null);
        if(order == null) return null;

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);
        return orderResponseDTO;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
        for(Orders order: orders) {
            orderResponseDTOS.add(new OrderResponseDTO(order));
        }
        return orderResponseDTOS;
    }

    @Override
    public boolean deleteOrderById(int id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
