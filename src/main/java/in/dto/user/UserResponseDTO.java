package in.dto.user;

import in.entity.Orders;
import in.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    private List<Integer> orders = new ArrayList<>();

    public UserResponseDTO(User user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        for(Orders order: user.getOrdersList()) {
            this.orders.add(order.getOrdersId());
        }
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Integer> getOrders() {
        return orders;
    }
}
