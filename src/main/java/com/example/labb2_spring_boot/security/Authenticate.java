package com.example.labb2_spring_boot.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authenticate {

    public static String getAuthenticatedUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth instanceof AnonymousAuthenticationToken || auth == null) ? null : auth.getName();
    }
}
