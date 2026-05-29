package com.biblioteca.rota_backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private final String chaveSecreta = "chave-super-secreta-emakers";

    public String gerarPasseDeAcesso(String identificadorUsuario) {
        Algorithm algoritmo = Algorithm.HMAC256(chaveSecreta);
        
        return JWT.create()
                .withIssuer("rota_backend")
                .withSubject(identificadorUsuario)
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign(algoritmo);
    }

    public String decifrarPasseDeAcesso(String passeRecebido) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(chaveSecreta);
            return JWT.require(algoritmo)
                    .withIssuer("rota_backend")
                    .build()
                    .verify(passeRecebido)
                    .getSubject();
                    
        } catch (Exception erro) {
            System.out.println("Falha na leitura do Token: " + erro.getMessage());
            return null;
        }
    }
}