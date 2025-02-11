package in.service;

import in.dto.user.UserResponseDTO;
import in.entity.User;

import java.util.List;

public interface UserService {
    public int createUser(User user);
    public UserResponseDTO getUserById(int id);
    public List<UserResponseDTO> getAllUsers();
    public boolean deleteById(int id);
}
