package com.biblioteca.rota_backend.security;

import com.biblioteca.rota_backend.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class FiltroDeSeguranca extends OncePerRequestFilter {

    private final TokenService tokenService;

    public FiltroDeSeguranca(TokenService tokenServico) {
        this.tokenService = tokenServico;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        if (token != null) {
            String emailUsuario = tokenService.decifrarPasseDeAcesso(token);
            
            if (emailUsuario != null) {
                var autenticacao = new UsernamePasswordAuthenticationToken(emailUsuario, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String cabecalho = request.getHeader("Authorization");
        if (cabecalho == null || !cabecalho.startsWith("Bearer ")) {
            return null;
        }
        return cabecalho.replace("Bearer ", "");
    }
}