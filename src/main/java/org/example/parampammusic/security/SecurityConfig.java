package org.example.parampammusic.security;

import jakarta.servlet.DispatcherType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

/**
 * Конфигурация безопасности приложения.
 * Настраивает параметры безопасности, такие как управление доступом,
 * обработка аутентификации и авторизации, а также настройки сессий.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);
    /**
     * Конфигурирует цепочку фильтров безопасности.
     *
     * @param httpSecurity объект HttpSecurity для настройки безопасности
     * @return настроенная цепочка фильтров безопасности
     * @throws Exception если возникает ошибка конфигурации
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("Конфигурация настроек безопасности...");
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/album/**", "/track/**", "/artist/**", "/profile/**", "/cart/**", "/review/**", "/myTrack/**")
                                        .hasAnyRole("ADMIN","USER")
                        .requestMatchers("/", "/registration", "/registrationForm","/custom-login", "/logout", "/welcomePage/**")
                                        .permitAll()
                        .requestMatchers("/resources/**","/img/**","/css/**")
                                        .permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/main").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/custom-login")
                        .loginProcessingUrl("/loginForm")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/main", true)
                        .failureUrl("/loginError")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .addLogoutHandler((request, response, authentication) -> {
                            if (authentication != null) {
                                logger.info("Пользователь {} вышел из системы.", authentication.getName());
                            }
                        })
                )
                .sessionManagement(session -> session
                        .sessionFixation().newSession()
                );
        logger.info("Конфигурация настроек безопасности успешно завершена.");

        return httpSecurity.build();
    }

    /**
     * Определяет менеджер аутентификации.
     *
     * @param authenticationConfiguration конфигурация аутентификации
     * @return менеджер аутентификации
     * @throws Exception если возникает ошибка при получении менеджера аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("Настройка менеджера аутентификации...");
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Определяет кодировщик паролей.
     *
     * @return кодировщик паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Инициализация кодировщика паролей...");
        return new BCryptPasswordEncoder();
    }

    /**
     * Определяет диалект Spring Security для использования в шаблонах.
     *
     * @return диалект Spring Security
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        logger.info("Инициализация SpringSecurityDialect для шаблонов...");
        return new SpringSecurityDialect();
    }
}
