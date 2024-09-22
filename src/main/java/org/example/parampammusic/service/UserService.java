package org.example.parampammusic.service;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.*;
import org.example.parampammusic.repository.AudioTrackRepository;
import org.example.parampammusic.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserService.class);


    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleService roleService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user, String rawPassword, int roleId) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        Role role = roleService.getRoleById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public boolean authenticate(String login, String rawPassword, HttpSession session) {
        User user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            session.setAttribute("login", user.getLogin());
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            System.out.println("User found: " + user.getLogin());
            Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
            grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
            return new org.springframework.security.core.userdetails.User(user.getLogin(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleName())));
        } else {
            logger.info("User found: {}", user.getLogin());
            throw new UsernameNotFoundException("Cannot find user with login=" + login);
        }
    }

    public Integer getUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userRepository.findByLogin(userDetails.getUsername());
            if (user != null) {
                return user.getId();
            }
        }
        return null;
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByLogin(login);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<AudioTrack> getPurchasedTracksForUser(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Order> orders = user.getOrders();
            List<AudioTrack> purchasedTracks = new ArrayList<>();
            for (Order order : orders) {
                if (order.getStatus() == OrderStatus.COMPLETED) {
                    List<? extends AudioTrack> audioTracks = order.getOrderItems().stream()
                            .map(OrderItem::getAudioTrack)
                            .collect(Collectors.toList());
                    purchasedTracks.addAll(audioTracks);
                }
            }
            return purchasedTracks;
        }
        return Collections.emptyList();
    }

    public void updateUser(Integer userId, String email, String telNumber) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEmail(email);
            user.setTelNumber(telNumber);
            userRepository.save(user);
        }
    }

    public void addBonusPoints(Integer userId, int bonusPoints) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setBonusPoint(user.getBonusPoint() + bonusPoints);
            userRepository.save(user);
        }
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

}
