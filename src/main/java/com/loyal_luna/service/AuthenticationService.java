package com.loyal_luna.service;

import com.loyal_luna.config.JWTProperties;
import com.loyal_luna.core.JWTService;
import com.loyal_luna.dto.request.AuthenticationRequest;
import com.loyal_luna.dto.request.IntrospectRequest;
import com.loyal_luna.dto.response.AuthenticationResponse;
import com.loyal_luna.dto.response.IntrospectResponse;
import com.loyal_luna.exception.AppException;
import com.loyal_luna.exception.ErrorCode;
import com.loyal_luna.mapper.UserMapper;
import com.loyal_luna.repository.UserRepository;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.*;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    JWTProperties jwtProperties;
    JWTService jwtService;

    public AuthenticationResponse login(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = jwtService.generateToken(user.getId());
        log.info("token" + token);
        return AuthenticationResponse.builder().user(userMapper.toUserResponse(user)).access_token(token).build();

    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {

        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(jwtProperties.getKey());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expityTime.after(new Date()))
                .build();

    }


}
