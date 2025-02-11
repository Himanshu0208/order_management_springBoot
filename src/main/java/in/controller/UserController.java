package in.controller;

import in.dto.user.UserResponseDTO;
import in.entity.User;
import in.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<UserResponseDTO> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/")
    public int createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable int id) {
        return userService.deleteById(id);
    }

}
