package com.loyal_luna.service;

import com.loyal_luna.mapper.UserMapper;
import lombok.AccessLevel;
import com.loyal_luna.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.loyal_luna.exception.ErrorCode;
import com.loyal_luna.exception.AppException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.loyal_luna.dto.response.UserResponse;
import com.loyal_luna.repository.UserRepository;
import com.loyal_luna.dto.request.UserCreateRequest;
import com.loyal_luna.dto.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@RequiredArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public List<UserResponse> index() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse store(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));

    }

    public UserResponse show(String id) {
        User user = this.findById(id);
        return userMapper.toUserResponse(user);
    }

    public UserResponse update(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userMapper.updateUser(user, request);
            userRepository.save(user);
        }
        return userMapper.toUserResponse(user);
    }

    public void delete(String id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
