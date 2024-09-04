package org.example.parampammusic.util;

import org.example.parampammusic.DTO.UserDTO;
import org.example.parampammusic.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);

    User updateUser(UserDTO userDTO);

    void deleteUser(int userId);

    List<User> getAllUsers();

    User getUserByLogin(String login);
}
