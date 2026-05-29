package com.biblioteca.rota_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca {

    private final FiltroDeSeguranca filtroDeSeguranca;

    public ConfiguracaoDeSeguranca(FiltroDeSeguranca filtroDeSeguranca) {
        this.filtroDeSeguranca = filtroDeSeguranca;
    }

    @Bean
    public SecurityFilterChain correnteDeFiltros(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desativa proteções de formulário web antigo (usamos API)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API REST não guarda sessão
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/pessoas/login").permitAll() // Libera a futura rota de Login
                        .requestMatchers(HttpMethod.POST, "/pessoas").permitAll() // Libera qualquer um para se cadastrar
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Libera a tela do Swagger
                        .anyRequest().authenticated() // Bloqueia todo o resto (Livros, Empréstimos, etc)
                )
                .addFilterBefore(filtroDeSeguranca, UsernamePasswordAuthenticationFilter.class) // Adiciona o nosso filtro
                .build();
    }
}