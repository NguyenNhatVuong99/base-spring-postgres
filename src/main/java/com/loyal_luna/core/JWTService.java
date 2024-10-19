package com.loyal_luna.core;

import com.loyal_luna.config.JWTProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JWTService {
    JWTProperties jwtProperties;

    public String generateToken(String id) {
        JWSHeader header = new JWSHeader(jwtProperties.getAlgorithm());
        Payload payload = payload(id);
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(jwtProperties.getKey()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token");
            throw new RuntimeException(e);
        }
    }

    private Payload payload(String id) {
        var now = Instant.now();
        var expiresIn = now.plus(jwtProperties.getExpiresIn());

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(id)
                .issuer(jwtProperties.getIssuer()).issueTime(Date.from(now))
                .expirationTime(Date.from(expiresIn))
                .build();
        return new Payload(jwtClaimsSet.toJSONObject());
    }
}
