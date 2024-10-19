package com.loyal_luna.config;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JWSAlgorithm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Base64;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "app.jwt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTProperties {
    byte[] key;
    JWSAlgorithm algorithm;
    String issuer;
    Duration expiresIn;

    public void setKey(String key) {
        this.key = Base64.getDecoder().decode(key);
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = JWSAlgorithm.parse(algorithm);
    }

    public String getStringAlgorithm() {
        return algorithm.toString();
    }
}
