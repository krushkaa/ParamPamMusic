package org.example.parampammusic.controller;

import lombok.RequiredArgsConstructor;
import org.example.parampammusic.DTO.UserDTO;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.util.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registrationUser")
    public User registerUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
}
