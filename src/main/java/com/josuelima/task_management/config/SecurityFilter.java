package com.josuelima.task_management.config;

import com.josuelima.task_management.repositories.UserRepositoty;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepositoty userRepositoty;

    public SecurityFilter(TokenService tokenService, UserRepositoty userRepositoty) {
        this.userRepositoty = userRepositoty;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if(token != null){
            String login = tokenService.validateToken(token);
            Optional<UserDetails> user = userRepositoty.findByUsername(login);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null) return null;
        return authorizationHeader.replace("Bearer ", "");
    }
}
