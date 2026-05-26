package com.biblioteca.rota_backend.service;

import com.biblioteca.rota_backend.model.Livro;
import com.biblioteca.rota_backend.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Integer idLivro) {
        return livroRepository.findById(idLivro)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado no sistema!"));
    }

    public Livro atualizarLivro(Integer idLivro, Livro livroAtualizado) {
        Livro livroExistente = buscarPorId(idLivro);
        
        livroExistente.setNome(livroAtualizado.getNome());
        livroExistente.setAutor(livroAtualizado.getAutor());
        livroExistente.setDataLancamento(livroAtualizado.getDataLancamento());
        
        return livroRepository.save(livroExistente);
    }

    public void deletarLivro(Integer idLivro) {
        Livro livroExistente = buscarPorId(idLivro);
        livroRepository.delete(livroExistente);
    }
}