package org.trigear.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trigear.dto.coupons.CouponUsageDTO;
import org.trigear.dto.user.UserDTO;
import org.trigear.entity.Coupons;
import org.trigear.entity.User;
import org.trigear.exception.user.UserAlreadyExists;
import org.trigear.exception.user.UserNotFound;
import org.trigear.repository.CouponRepository;
import org.trigear.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final Logger serverLogs = LoggerFactory.getLogger("SERVER_LOGS");

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final CouponUsageService couponUsageService;

    @Autowired
    UserServiceImplementation(
            UserRepository userRepository,
            CouponRepository couponRepository,
            CouponUsageService couponUsageService)
    {
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
        this.couponUsageService = couponUsageService;
    }

    @Override
    public int createUser(UserDTO newUser) {
        serverLogs.trace("Executing createUser");
        try {
            serverLogs.debug("Fetching user with given email");
            if(userRepository.findByEmail(newUser.getEmail()) != null) {
                throw new UserAlreadyExists("User with given email already exists");
            }
            serverLogs.debug("No user exists with given email, hence can create new user");

            User user = new User(newUser);

            serverLogs.debug("Saving user to DB");
            userRepository.save(user);
            serverLogs.debug("Saving user to DB: Completed");

            serverLogs.trace("Executing createUser completed");
            return user.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }

    @Override
    public UserDTO getUserById(int id) {
        serverLogs.trace("Executing getUserById");
        try {
            serverLogs.debug("Fetching user with id#{}", id);
            User user = userRepository.findById(id).orElse(null);
            serverLogs.debug("Fetching user with id#{}: completed", id);

            if (user == null) {
                throw new UserNotFound("No user with id#"+id+" exists");
            }

            UserDTO userDTO = new UserDTO(user);
            serverLogs.trace("Executing getUserById completed");
            return userDTO;
        } catch(Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        serverLogs.trace("Executing getAllUsers");
        try {
            serverLogs.debug("Fetching all users");
            List<User> users = userRepository.findAll();
            serverLogs.debug("Fetching all users: Completed");

            List<UserDTO> userDTOS = new ArrayList<>();
            serverLogs.trace("Converting User into User DTO");
            for (User user : users) {
                userDTOS.add(new UserDTO(user));
            }

            serverLogs.trace("Executing getAllUsers completed");
            return userDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

    @Override
    public boolean deleteById(int id) {
        serverLogs.trace("Executing deleteById");
        try {
            UserDTO user = getUserById(id);

            serverLogs.debug("Deleting user by id#{}",id);
            userRepository.deleteById(id);
            serverLogs.debug("Deleting user by id:#{} completed", id);

            serverLogs.trace("Executing deleteById completed");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<CouponUsageDTO> getAllowedCoupons(int id) {
        serverLogs.trace("Executing getAllowedCoupons");
        try {
            serverLogs.trace("Fetching user with id#{}",id);
            User user = userRepository.findById(id).orElse(null);
            if(user == null) {
                throw new UserNotFound("There is no user with id#"+id);
            }

            List<CouponUsageDTO> allowedCoupon = new ArrayList<>();

            serverLogs.debug("Fetching all coupons");
            List<Coupons> allCoupons = couponRepository.findAll();
            serverLogs.debug("Fetching all coupons: completed");

            serverLogs.trace("Extracting all allowed coupons");
            for(Coupons coupon : allCoupons) {
                if(coupon.getMaxAllowed() == -1 && couponUsageService.canUse(user, coupon)) {
                    allowedCoupon.add(new CouponUsageDTO(coupon));
                }
                else if(coupon.getMaxAllowed() > coupon.getCurrUsed() && couponUsageService.canUse(user, coupon)) {
                    allowedCoupon.add(new CouponUsageDTO(coupon));
                }
            }

            serverLogs.trace("Executing getAllowedCoupons completed");
            return allowedCoupon;
        } catch (Exception e) {
            e.printStackTrace();
            serverLogs.error(e.getMessage());
            serverLogs.error(Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

}
