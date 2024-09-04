package org.example.parampammusic.service;

import lombok.RequiredArgsConstructor;
import org.example.parampammusic.DTO.UserDTO;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.repository.UserRepository;
import org.example.parampammusic.util.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setTelNumber(userDTO.getTelNumber());
        user.setBonusPoint(Integer.parseInt(userDTO.getBonusPoint()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User user = userRepository.findByLogin(userDTO.getLogin());
        if (user != null) {
            user.setEmail(userDTO.getEmail());
            user.setTelNumber(userDTO.getTelNumber());
            user.setBonusPoint(Integer.parseInt(userDTO.getBonusPoint()));

            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
        } else {
            System.out.println("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
