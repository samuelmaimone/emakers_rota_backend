CREATE TABLE pessoa (
    idPessoa INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    cep CHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE livro (
    idLivro INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    dataLancamento DATE NOT NULL
);

CREATE TABLE emprestimo (
    idEmprestimo INT AUTO_INCREMENT PRIMARY KEY,
    idPessoa INT NOT NULL,
    idLivro INT NOT NULL,
    FOREIGN KEY (idPessoa) REFERENCES pessoa(idPessoa),
    FOREIGN KEY (idLivro) REFERENCES livro(idLivro)
);