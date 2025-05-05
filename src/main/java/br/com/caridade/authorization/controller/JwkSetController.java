package br.com.caridade.authorization.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
@AllArgsConstructor
public class JwkSetController {

    private final RSAPublicKey publicKey;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getJwk() {
        RSAKey key = new RSAKey.Builder(publicKey)
                .build();

        return new JWKSet(key).toJSONObject();
    }
}
