package com.loyal_luna.controller;

import lombok.AccessLevel;
import jakarta.validation.Valid;
import com.loyal_luna.core.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.loyal_luna.service.UserService;
import com.loyal_luna.dto.response.UserResponse;
import org.springframework.web.bind.annotation.*;
import com.loyal_luna.dto.request.UserCreateRequest;
import com.loyal_luna.dto.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> index() {
        return ApiResponse.<List<UserResponse>>builder().result(userService.index()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> show(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder().result(userService.show(id)).build();
    }

    @PostMapping
    public ApiResponse<UserResponse> store(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder().result(userService.store(request)).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserResponse> update(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder().result(userService.update(id, request)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable String id) {
        userService.delete(id);
        return ApiResponse.<String>builder().message("Delete success").build();
    }
}
