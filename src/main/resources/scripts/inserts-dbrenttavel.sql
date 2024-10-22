INSERT INTO `anfitriao` (`nome`, `email`, `senha`) VALUES 
('Tio Paulo', 'tio.paulo@email.com', '2288821c6b799cf47a8c9aa231f361ffb906bbee0d5fb5e1767509e27442cc62'), -- senha: senha321
('Joao Silva','joao.silva@email.com', '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'); -- senha: senha123


INSERT INTO `endereco` (`numero`, `cep`, `rua`, `bairro`, `cidade`, `estado`, `pais`, `id_anfitriao`) VALUES 
(101, '12345678', 'Rua das Flores', 'Jardim Primavera', 'São Paulo', 'SP', 'Brasil', 1),
(202, '87654321', 'Avenida dos Bosques', 'Vila Verde', 'Rio de Janeiro', 'RJ', 'Brasil', 1),
(303, '11223344', 'Rua das Palmeiras', 'Centro', 'Curitiba', 'PR', 'Brasil', 1),
(404, '44332211', 'Avenida das Nações', 'Bela Vista', 'Porto Alegre', 'RS', 'Brasil', 2),
(505, '55667788', 'Rua das Acácias', 'Jardim América', 'Belo Horizonte', 'MG', 'Brasil', 2);

INSERT INTO `imovel` (`nome`, `tipo`, `capacidadePessoas`, `qtdQuarto`, `qtdCama`, `qtdBanheiro`, `descricao`, `ocupado`, `id_anfitriao`, `id_endereco`) VALUES 
('Casa na Praia', 1, 6, 3, 4, 2, 'Casa ampla com vista para o mar', FALSE, 1, 1),
('Apartamento Central', 2, 2, 1, 1, 1, 'Apartamento aconchegante no centro da cidade', FALSE, 1, 1),
('Chalé', 3, 4, 2, 2, 1, 'Quarto compartilhado em uma casa tranquila', FALSE, 2, 2),
('Casa na Montanha', 1, 8, 4, 5, 3, 'Casa confortável com vista para as montanhas', FALSE, 2, 2),
('Apartamento Moderno', 2, 3, 2, 2, 1, 'Apartamento com design moderno e bem localizado', FALSE, 1, 3),
('Cabana Rústica', 3, 5, 3, 4, 2, 'Cabana rústica em área rural', FALSE, 1, 4),
('Casa de Campo', 1, 10, 5, 6, 4, 'Ampla casa de campo com piscina', FALSE, 1, 4),
('Studio Urbano', 2, 2, 1, 1, 1, 'Studio compacto e bem equipado', FALSE, 1, 5);

INSERT INTO `inquilino` (`nome`, `email`, `telefone`, `id_anfitriao`) VALUES 
('Maria Oliveira','maria.oliveira@example.com', '1111111111', 1),
('Pedro Souza','pedro.souza@example.com', '2222222222', 1),
('Ana Lima','ana.lima@example.com', '3333333333', 1),
('Carlos Pereira','carlos.pereira@example.com', '4444444444', 2),
('Lucas Rodrigues','lucas.rodrigues@example.com', '5555555555', 2);

INSERT INTO `aluguel` (`data_checkin`, `data_checkoutPrevisto`, `data_checkoutEfetivo`, `valorTotal`, `valorDiaria`, `valorLimpeza`, `valorMulta`, `qtdDias`, `id_imovel`, `id_inquilino`, `id_anfitriao`) VALUES 
('2023-05-01 14:00:00', '2023-05-07 11:00:00', '2023-05-07 11:00:00', 1300.00, 200.00, 100.00, 0.00, 6, 1, 1, 1),
('2023-06-01 14:00:00', '2023-06-10 11:00:00', '2023-06-10 11:00:00', 2800.00, 300.00, 100.00, 0.00, 9, 1, 2, 1),
('2023-07-01 14:00:00', '2023-07-05 11:00:00', '2023-07-05 11:00:00', 900.00, 200.00, 100.00, 0.00, 4, 2, 3, 1),
('2023-08-01 14:00:00', '2023-08-15 11:00:00', '2023-08-15 11:00:00', 4400.00, 300.00, 200.00, 0.00, 14, 3, 4, 2),
('2023-09-01 14:00:00', '2023-09-03 11:00:00', '2023-09-03 11:00:00', 500.00, 200.00, 100.00, 0.00, 2, 3, 5, 2);