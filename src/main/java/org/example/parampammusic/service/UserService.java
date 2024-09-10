package org.example.parampammusic.service;

import lombok.RequiredArgsConstructor;
import org.example.parampammusic.entity.Role;
import org.example.parampammusic.entity.User;
import org.example.parampammusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(User user, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        Role role = roleService.getRoleByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public boolean authenticate(String login, String rawPassword) {
        User user = userRepository.findByLogin(login);
        return user != null && passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
            grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthoritySet);
        } else {
            throw new UsernameNotFoundException("Cannot find user with login=" + login);
        }
    }

//    public User updateUser(User user) {
//        User user = userRepository.findByLogin(user.getLogin());
//        if (user != null) {
//            user.setEmail(user.getEmail());
//            user.setTelNumber(user.getTelNumber());
//            user.setBonusPoint(user.getBonusPoint());
//
//            return userRepository.save(user);
//        } else {
//            return null;
//        }
//    }
//
//    public void deleteUser(int userId) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            userRepository.delete(user);
//        } else {
//            System.out.println("User not found");
//        }
//    }
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User getUserByLogin(String login) {
//        return userRepository.findByLogin(login);
//    }
}
