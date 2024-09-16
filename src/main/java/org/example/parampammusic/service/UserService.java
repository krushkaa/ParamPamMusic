package org.example.parampammusic.service;

import jakarta.servlet.http.HttpSession;
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

    public boolean authenticate(String login, String rawPassword, HttpSession session) {
        User user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            session.setAttribute("login", user.getLogin());
            return true;
        }
        return true;
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
}
