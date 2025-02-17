package org.trigear.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.dto.coupons.CouponUsageDTO;
import org.trigear.dto.user.UserDTO;
import org.trigear.entity.CouponUsage;
import org.trigear.entity.User;
import org.trigear.service.CouponsService;
import org.trigear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");

    private final UserService userService;
    private final CouponsService couponsService;

    @Autowired
    UserController(UserService userService, CouponsService couponsService) {
        this.userService = userService;
        this.couponsService = couponsService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/allowedCoupons/{id}")
    public List<CouponUsageDTO> getAllowedCoupons(@PathVariable int id) {
        serverLogs.info("get - /user/allowedCoupons/{id} = executing getAllowedCoupons(int id)");
        List<CouponUsageDTO> coupons = userService.getAllowedCoupons(id);
        serverLogs.info("get - /user/allowedCoupons/{id} = executing getAllowedCoupons(int id)");
        return coupons;
    }

    @PostMapping
    public int createUser(@RequestBody UserDTO user) {
        serverLogs.info("get - /user = executing createUser(UserDTO user)");
        int id = userService.createUser(user);
        serverLogs.info("get - /user = successfully executed createUser(UserDTO user)");
        return id;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable int id) {
        serverLogs.info("get - /user/{id} = executing getUser(int id)");
        UserDTO user = userService.getUserById(id);
        serverLogs.info("get - /user/{id} = successfully executed getUser(int id)");
        return user;
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable int id) {
        serverLogs.info("delete - /user/{id} = executing getUser(int id)");
        boolean isDeleted = userService.deleteById(id);
        serverLogs.info("delete - /user/{id} = successfully executed getUser(int id)");
        return isDeleted;
    }

}
