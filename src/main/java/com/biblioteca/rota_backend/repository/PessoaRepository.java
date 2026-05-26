package com.biblioteca.rota_backend.repository;

import com.biblioteca.rota_backend.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
