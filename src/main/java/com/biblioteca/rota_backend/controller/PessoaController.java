package com.biblioteca.rota_backend.controller;

import com.biblioteca.rota_backend.service.TokenService;
import org.springframework.web.client.RestTemplate;
import com.biblioteca.rota_backend.model.Pessoa;
import com.biblioteca.rota_backend.service.PessoaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    private final TokenService tokenService;

    public PessoaController(PessoaService pessoaService, TokenService tokenService) {
        this.pessoaService = pessoaService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaService.cadastrarPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar() {
        return ResponseEntity.ok(pessoaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(pessoaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Integer id, @Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.atualizarPessoa(id, pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/endereco/{cep}")
    public ResponseEntity<Object> consultarEnderecoViaCep(@PathVariable String cep) {

        RestTemplate restTemplate = new RestTemplate();
        
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        
        Object endereco = restTemplate.getForObject(url, Object.class);
        
        return ResponseEntity.ok(endereco);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Pessoa dadosLogin) {
        for (Pessoa pessoaRegistada : pessoaService.listarTodas()) {
            if (pessoaRegistada.getEmail().equals(dadosLogin.getEmail()) &&
                pessoaRegistada.getSenha().equals(dadosLogin.getSenha())) {
                
                String token = tokenService.gerarPasseDeAcesso(pessoaRegistada.getEmail());
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(401).body("Credenciais inválidas. Verifique o email e a palavra-passe.");
    }
}