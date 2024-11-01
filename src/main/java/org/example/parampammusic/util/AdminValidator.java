package org.example.parampammusic.util;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
/**
 * Компонент для валидации прав администратора.
 * Проверяет, является ли текущий аутентифицированный пользователь администратором.
 */
@Component
public class AdminValidator {

    /**
     * Проверяет, имеет ли текущий пользователь роль администратора.
     *
     * @param authentication объект, содержащий данные аутентификации пользователя.
     * @throws BadCredentialsException если пользователь не имеет роли "ADMIN".
     */
    public void validateAdmin (Authentication authentication){
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals("ADMIN"));
        if(!isAdmin){
            throw new BadCredentialsException("Доступ разрешен только администратору.");
        }
    }
}
