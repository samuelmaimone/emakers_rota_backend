package com.biblioteca.rota_backend.controller;

import com.biblioteca.rota_backend.model.Emprestimo;
import com.biblioteca.rota_backend.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/pessoa/{idPessoa}/livro/{idLivro}")
    public ResponseEntity<Emprestimo> emprestar(@PathVariable Integer idPessoa, @PathVariable Integer idLivro) {
        Emprestimo novoEmprestimo = emprestimoService.emprestarLivro(idPessoa, idLivro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo);
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listar() {
        return ResponseEntity.ok(emprestimoService.listarEmprestimos());
    }

    @DeleteMapping("/{idEmprestimo}")
    public ResponseEntity<Void> devolver(@PathVariable Integer idEmprestimo) {
        emprestimoService.devolverLivro(idEmprestimo);
        return ResponseEntity.noContent().build(); // Retorna Status 204 (Sucesso, mas sem conteúdo visual)
    }
}