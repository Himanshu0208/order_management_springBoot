package org.trigear.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trigear.dto.coupons.CouponUsageDTO;
import org.trigear.dto.coupons.CouponsDTO;
import org.trigear.dto.orders.OrderDTO;
import org.trigear.entity.CouponUsage;
import org.trigear.entity.Coupons;
import org.trigear.entity.Orders;
import org.trigear.entity.User;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    private List<OrderDTO> orders = new ArrayList<>();

    public UserDTO(User user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        for(Orders order: user.getOrdersList()) {
            this.orders.add(new OrderDTO(order));
        }
    }
}
