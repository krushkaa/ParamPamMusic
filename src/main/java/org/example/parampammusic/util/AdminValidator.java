package org.example.parampammusic.util;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AdminValidator {

    public void validateAdmin (Authentication authentication){
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals("ADMIN"));
        if(!isAdmin){
            throw new BadCredentialsException("Доступ разрешен только администратору.");
        }
    }
}
