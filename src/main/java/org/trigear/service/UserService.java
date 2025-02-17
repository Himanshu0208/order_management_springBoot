package org.trigear.service;

import org.trigear.dto.coupons.CouponUsageDTO;
import org.trigear.dto.user.UserDTO;
import org.trigear.entity.User;

import java.util.List;

public interface UserService {
    public int createUser(UserDTO user);
    public UserDTO getUserById(int id);
    public List<UserDTO> getAllUsers();
    public boolean deleteById(int id);
    public List<CouponUsageDTO> getAllowedCoupons(int id);
}
