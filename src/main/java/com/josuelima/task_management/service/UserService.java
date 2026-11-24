package com.josuelima.task_management.service;

import com.josuelima.task_management.dto.login.request.RegisterUserRequestDto;
import com.josuelima.task_management.dto.login.response.RegisterUserResponseDto;
import com.josuelima.task_management.exceptions.EmailAlreadyExistsException;
import com.josuelima.task_management.exceptions.UserAlreadyExistsException;
import com.josuelima.task_management.mapper.UserMapper;
import com.josuelima.task_management.model.User;
import com.josuelima.task_management.repositories.UserRepositoty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepositoty userRepositoty;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoty userRepositoty, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepositoty = userRepositoty;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto) {
        User user = userMapper.toUser(registerUserRequestDto);

        this.validateRegisterUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepositoty.save(user);
        return userMapper.toRegisterUserResponseDto(user);


    }

    private void validateRegisterUser(User user) {

        if (userRepositoty.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(String.format("O usuário [%s] já existe.", user.getUsername()));
        }

        if (userRepositoty.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(String.format("Esse E-mail [%s] já existe.", user.getEmail()));
        }
    }
}
