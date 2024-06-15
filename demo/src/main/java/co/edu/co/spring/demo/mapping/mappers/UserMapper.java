package co.edu.co.spring.demo.mapping.mappers;

import co.edu.co.spring.demo.domain.model.User;
import co.edu.co.spring.demo.mapping.DTO.UserDTO;

public class UserMapper {

    public static UserDTO mapFromModel(User user) {
        return new UserDTO(user.getUsername(), user.getPassword(), user.getEmail(),
                user.getFullName(), user.getCreatedAt(), user.getLastLogin());
    }

    public static User mapFromDTO(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.username())
                .password(userDTO.password())
                .email(userDTO.email())
                .fullName(userDTO.fullName())
                .createdAt(userDTO.createdAt())
                .lastLogin(userDTO.lastLogin())
                .build();
    }

}
