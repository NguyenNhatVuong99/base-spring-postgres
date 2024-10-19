package com.loyal_luna.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor()
public class ApiResponse<T> {
    @Builder.Default
    int code = 1000;
    String message;
    T result;
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();

}
