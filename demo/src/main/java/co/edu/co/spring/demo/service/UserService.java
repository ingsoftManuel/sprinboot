package co.edu.co.spring.demo.service;

import co.edu.co.spring.demo.domain.model.User;
import co.edu.co.spring.demo.mapping.DTO.UserDTO;
import co.edu.co.spring.demo.mapping.mappers.UserMapper;
import co.edu.co.spring.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.mapFromDTO(userDTO);
        return saveAndMapUser(user);
    }

    public UserDTO fetchUserById(Integer id) {
        return userRepository.findById(id)
                .map(UserMapper::mapFromModel)
                .orElse(null);
    }

    public List<UserDTO> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapFromModel)
                .collect(Collectors.toList());
    }

    public UserDTO modifyUser(Integer id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> updateUserDetails(existingUser, userDTO))
                .map(userRepository::save)
                .map(UserMapper::mapFromModel)
                .orElse(null);
    }

    public void removeUser(Integer id) {
        userRepository.deleteById(id);
    }

    public UserDTO authenticateUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(UserMapper::mapFromModel)
                .orElse(null);
    }

    private User updateUserDetails(User existingUser, UserDTO userDTO) {
        existingUser.setUsername(userDTO.username());
        existingUser.setPassword(userDTO.password());
        existingUser.setEmail(userDTO.email());
        existingUser.setFullName(userDTO.fullName());
        existingUser.setCreatedAt(userDTO.createdAt());
        existingUser.setLastLogin(userDTO.lastLogin());
        return existingUser;
    }

    private UserDTO saveAndMapUser(User user) {
        user = userRepository.save(user);
        return UserMapper.mapFromModel(user);
    }
}
