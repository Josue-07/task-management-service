package com.josuelima.task_management.dto.mapper;

import com.josuelima.task_management.dto.login.request.RegisterUserRequestDto;
import com.josuelima.task_management.dto.login.response.RegisterUserResponseDto;
import com.josuelima.task_management.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterUserRequestDto registerUserRequestDto);
    RegisterUserResponseDto toRegisterUserResponseDto(User user);
}
