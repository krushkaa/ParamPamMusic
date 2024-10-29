package org.example.parampammusic.service;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.parampammusic.entity.*;
import org.example.parampammusic.repository.ReviewRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сервис для управления пользователями и их аутентификацией.
 * Предоставляет методы для создания пользователей, аутентификации, получения текущего пользователя,
 * а также работы с ролями и бонусными баллами.
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RoleService roleService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для инициализации UserService с заданными репозиториями и кодировщиком паролей.
     *
     * @param userRepository  репозиторий для работы с пользователями
     * @param roleService     сервис для работы с ролями
     * @param orderService    сервис для работы с ролями
     * @param passwordEncoder кодировщик паролей
     */
    public UserService(UserRepository userRepository,
                       ReviewRepository reviewRepository,
                       RoleService roleService,
                       OrderService orderService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.roleService = roleService;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Создает нового пользователя с указанными данными.
     *
     * @param user        пользовательские данные.
     * @param rawPassword исходный пароль.
     * @param roleId      идентификатор роли пользователя.
     */
    public void createUser(User user, String rawPassword, int roleId) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        Role role = roleService.getRoleById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    /**
     * Аутентифицирует пользователя по логину и паролю.
     *
     * @param login       логин пользователя.
     * @param rawPassword исходный пароль.
     * @param session     сессия для сохранения информации о пользователе.
     * @return true, если аутентификация прошла успешно, иначе false.
     */
    public boolean authenticate(String login, String rawPassword, HttpSession session) {
        User user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            session.setAttribute("login", user.getLogin());
            return true;
        }
        return false;
    }

    /**
     * Загружает пользователя по его логину для аутентификации.
     *
     * @param login логин пользователя.
     * @return объект UserDetails с информацией о пользователе.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
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
            logger.info("User not found with login: {}", login);
            throw new UsernameNotFoundException("Cannot find user with login=" + login);
        }
    }

    /**
     * Возвращает идентификатор текущего пользователя.
     *
     * @return идентификатор текущего пользователя или null, если пользователь не найден.
     */
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

    /**
     * Ищет пользователя по логину.
     *
     * @param login логин пользователя.
     * @return объект User, соответствующий логину.
     */
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    /**
     * Возвращает текущего аутентифицированного пользователя.
     *
     * @return текущий пользователь.
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByLogin(login);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Оформляет покупки треков пользователя.
     *
     * @param items список позиций заказа
     * @param user  пользователь, который совершает покупку
     */
    public void purchaseTracks(List<OrderItem> items, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.COMPLETED);
        order.setOrderDate(java.time.LocalDate.now());

        double total = 0.0;
        for (OrderItem item : items) {
            item.setOrder(order);
            total += item.getPrice();
        }
        order.setTotalPrice(total);
        orderService.saveOrder(order);
    }

    /**
     * Обновляет email и телефонный номер пользователя.
     *
     * @param userId    идентификатор пользователя.
     * @param email     новый email.
     * @param telNumber новый телефонный номер.
     */
    public void updateUser(Integer userId, String email, String telNumber) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEmail(email);
            user.setTelNumber(telNumber);
            userRepository.save(user);
        }
    }

    /**
     * Добавляет бонусные баллы пользователю.
     *
     * @param userId      идентификатор пользователя.
     * @param bonusPoints количество добавляемых бонусных баллов.
     */
    public void addBonusPoints(Integer userId, int bonusPoints) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setBonusPoint(user.getBonusPoint() + bonusPoints);
            userRepository.save(user);
        }
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param userId идентификатор удаляемого пользователя.
     */
    @Transactional
    public void deleteUser(Integer userId) {
        reviewRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }
}
