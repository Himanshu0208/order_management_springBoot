package in.service;

import in.dto.user.UserResponseDTO;
import in.entity.User;
import in.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int createUser(User newUser) {
        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return newUser.getUserId();
    }

    @Override
    public UserResponseDTO getUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) return null;
        return new UserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for(User user: users) {
            userResponseDTOS.add(new UserResponseDTO(user));
        }
        return userResponseDTOS;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
