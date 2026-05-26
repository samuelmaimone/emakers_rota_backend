package com.biblioteca.rota_backend.service;

import com.biblioteca.rota_backend.model.Pessoa;
import com.biblioteca.rota_backend.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        if (pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new RuntimeException("Já existe uma pessoa cadastrada com este CPF!");
        }
        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            throw new RuntimeException("Já existe uma pessoa cadastrada com este e-mail!");
        }
        
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    public Pessoa buscarPorId(Integer idPessoa) {
        return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada no sistema!"));
    }

    public Pessoa atualizarPessoa(Integer idPessoa, Pessoa pessoaAtualizada) {
        Pessoa pessoaExistente = buscarPorId(idPessoa);
        
        pessoaExistente.setNome(pessoaAtualizada.getNome());
        pessoaExistente.setCpf(pessoaAtualizada.getCpf());
        pessoaExistente.setCep(pessoaAtualizada.getCep());
        pessoaExistente.setEmail(pessoaAtualizada.getEmail());
        pessoaExistente.setSenha(pessoaAtualizada.getSenha());
        
        return pessoaRepository.save(pessoaExistente);
    }

    public void deletarPessoa(Integer idPessoa) {
        Pessoa pessoaExistente = buscarPorId(idPessoa);
        pessoaRepository.delete(pessoaExistente);
    }
}