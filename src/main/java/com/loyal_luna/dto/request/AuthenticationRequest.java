package com.loyal_luna.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    String email;
    String password;
}
