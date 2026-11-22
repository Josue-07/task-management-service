package com.josuelima.task_management.controller;

import com.josuelima.task_management.config.TokenService;
import com.josuelima.task_management.dto.login.request.LoginRequestDto;
import com.josuelima.task_management.dto.login.request.RegisterUserRequestDto;
import com.josuelima.task_management.dto.login.response.LoginResponseDto;
import com.josuelima.task_management.dto.login.response.RegisterUserResponseDto;
import com.josuelima.task_management.model.User;
import com.josuelima.task_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        String token = tokenService.generateToken((User) authentication.getPrincipal());
        String expireAt = tokenService.getExpireDateToken(token);

        return ResponseEntity.ok(new LoginResponseDto(token, "Bearer", expireAt));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        RegisterUserResponseDto response = userService.registerUser(registerUserRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


}
