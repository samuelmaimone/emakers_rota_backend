package com.biblioteca.rota_backend.service;

import com.biblioteca.rota_backend.model.Emprestimo;
import com.biblioteca.rota_backend.model.Livro;
import com.biblioteca.rota_backend.model.Pessoa;
import com.biblioteca.rota_backend.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final PessoaService pessoaService;
    private final LivroService livroService;

    // O Spring injeta os 3 automaticamente pelo construtor
    public EmprestimoService(EmprestimoRepository emprestimoRepository, 
                             PessoaService pessoaService, 
                             LivroService livroService) {
        this.emprestimoRepository = emprestimoRepository;
        this.pessoaService = pessoaService;
        this.livroService = livroService;
    }

    public Emprestimo emprestarLivro(Integer idPessoa, Integer idLivro) {
        // 1. Verifica se a pessoa existe (se não existir, o buscarPorId já lança o erro)
        Pessoa pessoa = pessoaService.buscarPorId(idPessoa);
        
        // 2. Verifica se o livro existe
        Livro livro = livroService.buscarPorId(idLivro);

        // 3. Cria a relação de empréstimo
        Emprestimo novoEmprestimo = new Emprestimo();
        novoEmprestimo.setPessoa(pessoa);
        novoEmprestimo.setLivro(livro);

        // 4. Salva no banco
        return emprestimoRepository.save(novoEmprestimo);
    }

    public void devolverLivro(Integer idEmprestimo) {
        // Busca o empréstimo para garantir que ele existe
        Emprestimo emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado!"));
        
        // Para "devolver", nós deletamos o registro da tabela de relação
        emprestimoRepository.delete(emprestimo);
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }
}