INSERT INTO `anfitriao` (`email`, `senha`) VALUES
('joao.silva@example.com', 'senha123');

INSERT INTO `endereco` (`numero`, `cep`, `rua`, `bairro`, `cidade`, `estado`, `pais`) VALUES
(101, '12345678', 'Rua das Flores', 'Jardim Primavera', 'São Paulo', 'SP', 'Brasil'),
(202, '87654321', 'Avenida dos Bosques', 'Vila Verde', 'Rio de Janeiro', 'RJ', 'Brasil');

INSERT INTO `imovel` (`nome`, `tipo`, `capacidadePessoas`, `qtdQuarto`, `qtdCama`, `qtdBanheiro`, `descricao`, `id_anfitriao`, `id_endereco`) VALUES
('Casa na Praia', 1, 6, 3, 4, 2, 'Casa ampla com vista para o mar', 1, 1),
('Apartamento Central', 2, 2, 1, 1, 1, 'Apartamento aconchegante no centro da cidade', 1, 1),
('Quarto Compartilhado', 3, 4, 2, 2, 1, 'Quarto compartilhado em uma casa tranquila', 1, 2);

INSERT INTO `inquilino` (`nome`, `email`, `telefone`) VALUES
('Maria Oliveira','maria.oliveira@example.com', '1111111111'),
('Pedro Souza','pedro.souza@example.com', '2222222222'),
('Ana Lima','ana.lima@example.com', '3333333333'),
('Carlos Pereira','carlos.pereira@example.com', '4444444444'),
('Lucas Rodrigues','lucas.rodrigues@example.com', '5555555555');

INSERT INTO `aluguel` (`data_checkin`, `data_checkoutPrevisto`, `data_checkoutEfetivo`, `valorTotal`, `valorDiaria`, `valorLimpeza`, `valorMulta`, `qtdDias`, `ocupado`, `id_imovel`, `id_inquilino`) VALUES
('2024-05-01 14:00:00', '2024-05-07 11:00:00', '2024-05-07 11:00:00', 1300.00, 200.00, 100.00, 0.00, 6, TRUE, 1, 1),
('2024-06-01 14:00:00', '2024-06-10 11:00:00', '2024-06-10 11:00:00', 2800.00, 300.00, 100.00, 0.00, 9, TRUE, 1, 2),
('2024-07-01 14:00:00', '2024-07-05 11:00:00', '2024-07-05 11:00:00', 900.00, 200.00, 100.00, 0.00, 4, TRUE, 2, 3),
('2024-08-01 14:00:00', '2024-08-15 11:00:00', '2024-08-15 11:00:00', 4400.00, 300.00, 200.00, 0.00, 14, TRUE, 3, 4),
('2024-09-01 14:00:00', '2024-09-03 11:00:00', '2024-09-03 11:00:00', 500.00, 200.00, 100.00, 0.00, 2, TRUE, 3, 5);