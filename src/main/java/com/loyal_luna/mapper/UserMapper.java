package com.loyal_luna.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.loyal_luna.entity.User;
import com.loyal_luna.dto.response.UserResponse;
import com.loyal_luna.dto.request.UserCreateRequest;
import com.loyal_luna.dto.request.UserUpdateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
