package org.trigear.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.trigear.dto.orders.OrderDTO;
import org.trigear.dto.orders.OrderDetailsDTO;
import org.trigear.entity.*;
import org.trigear.exception.*;
import org.trigear.exception.coupon.CannotUseCoupon;
import org.trigear.exception.coupon.CouponNotFound;
import org.trigear.exception.order.OrderNotFound;
import org.trigear.exception.user.UserNotFound;
import org.trigear.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService{

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final CouponUsageService couponUsageService;

    @Autowired
    OrderServiceImplementation(OrderRepository orderRepository,
                               ProductRepository productRepository,
                               CouponRepository couponRepository,
                               UserRepository userRepository,
                               CouponUsageService couponUsageService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.couponUsageService = couponUsageService;
    }

    @Override
    public int createOrder(OrderDTO newOrder) {
        serverLogs.trace("Executing createOrder");
        try {
            serverLogs.debug("Fetching the user who ordered");
            User user = userRepository.findById(newOrder.getUserId()).orElse(null);
            if(user == null) {
                throw new UserNotFound("No user with id#"+newOrder.getUserId()+" found");
            }

            Coupons coupon = null;
            if(newOrder.getCoupon() != null) {
                int appliedCouponId =newOrder.getCoupon().getCouponId();

                serverLogs.debug("Fetching the coupon applied on order");
                coupon = couponRepository.findById(appliedCouponId).orElse(null);
            }

            serverLogs.trace("Creating the order object");
            Orders order = new Orders(user, coupon);

            if(newOrder.getCoupon() != null && coupon == null) {
                throw new CouponNotFound("Applied Coupon does not exist");
            }


            double amount = 0;
            List<OrderDetails> orderDetailsList = new ArrayList<>();

            serverLogs.trace("Calculating the amount and creating OrderDetailsList from the order");
            for(OrderDetailsDTO orderDetailsDTO: newOrder.getOrderDetails()) {
                int orderedProductId = orderDetailsDTO.getProductId();
                Product product = productRepository.findById(orderedProductId).orElse(null);

                if(product == null) {
                    throw new ProductNotFound("Can't find the product with id "+orderedProductId);
                }

                orderDetailsDTO.setPrice(product.getPrice());
                amount += (orderDetailsDTO.getPrice()*orderDetailsDTO.getQuantity());

                double price = orderDetailsDTO.getPrice();
                int quantity = orderDetailsDTO.getQuantity();
                OrderDetails orderDetails = new OrderDetails(quantity, price, order, product);
                orderDetailsList.add(orderDetails);
            }

            double discount = 0;
            if(coupon != null) {
                if(coupon.getMaxAllowed() != -1 && coupon.getMaxAllowed() <= coupon.getCurrUsed()) {
                    throw new CannotUseCoupon("Can't use this coupon: max used reached");
                }
                else if(!couponUsageService.canUse(user, coupon)) {
                    throw new CannotUseCoupon("Can't use this coupon: because of limited usage");
                }

                serverLogs.trace("Calculating discount");
                float percentage = coupon.getPercentage();
                double flat = coupon.getFlat();
                discount = Math.min(amount*percentage/100, flat);

                coupon.setCurrUsed(coupon.getCurrUsed()+1);

                serverLogs.trace("Checking that can the user use the applied coupon");
                if(couponUsageService.use(user, coupon) != -1) {
                    throw new CannotUseCoupon("Unable to save the coupon usage to database");
                };

                serverLogs.debug("Updating the coupon currUsed into DB");
                couponRepository.save(coupon);
            }

            order.setOrderDetailsList(orderDetailsList);
            order.setAmount(amount-discount);
            order.setOrderedAt(new Date());

            serverLogs.debug("Saving the order into DB");
            orderRepository.save(order);

            serverLogs.trace("Executing createOrder completed");
            return order.getOrdersId();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }

    @Override
    public OrderDTO getOrdersById(int id) {
        serverLogs.trace("Executing getOrdersById");
        try {
            serverLogs.debug("Fetching the order from the given id#{}", id);
            Orders order = orderRepository.findById(id).orElse(null);
            if (order == null) {
                throw new OrderNotFound("No order with given order id found");
            }

            serverLogs.trace("Creating OrderDTO from the fetched order");
            OrderDTO orderResponseDTO = new OrderDTO(order);

            serverLogs.trace("Executing getOrdersById completed");
            return orderResponseDTO;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        serverLogs.trace("Executing getAllOrders");
        try {
            serverLogs.debug("Fetching all orders from DB");
            List<Orders> orders = orderRepository.findAll();
            serverLogs.debug("Fetching all orders from DB: completed");

            List<OrderDTO> orderResponseDTOS = new ArrayList<>();
            serverLogs.trace("Creating OrderDTO from the order fetched");
            for (Orders order : orders) {
                orderResponseDTOS.add(new OrderDTO(order));
            }
            serverLogs.trace("Executing getAllOrders Completed");
            return orderResponseDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

    @Override
    public boolean deleteOrderById(int id) {
        serverLogs.debug("Executing deleteOrderById");
        try {
            serverLogs.debug("Fetching the order with id#{} to delete from DB",id);
            Orders order = orderRepository.findById(id).orElse(null);
            if(order == null) {
                throw new OrderNotFound("No order with order id#"+id+" exists");
            }

            serverLogs.debug("Deleting the order with id#{} to delete from DB",id);
            orderRepository.deleteById(id);
            serverLogs.debug("Successfully deleted the order with id#{} to delete from DB",id);

            serverLogs.debug("Executing deleteOrderById completed");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return false;
        }

    }
}
