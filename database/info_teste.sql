INSERT INTO servico (nome, sigla, descricao) VALUES
('Emissão de RG', 'RG', 'Primeira e segunda via de carteira de identidade'),
('CNH e Trânsito', 'CNH', 'Renovação, segunda via e pontuação da CNH'),
('Atestado de Antecedentes', 'ANT', 'Emissão de certidão de antecedentes criminais');

INSERT INTO guiche (numero, ocupado, contador_prioridade_consecutiva) VALUES
(1, FALSE, 0),
(2, FALSE, 0),
(3, FALSE, 0);

INSERT INTO guiche_servico (guiche_id, servico_id) VALUES
(1, 1), (1, 2),
(1, 3), (2, 1),
(3, 2), (3, 3);

INSERT INTO senha (codigo, servico_id, prioritaria, status, dt_emissao) VALUES
('P-RG-001', 1, TRUE,  'AGUARDANDO', NOW() - INTERVAL '10 minutes'),
('P-RG-002', 1, TRUE,  'AGUARDANDO', NOW() - INTERVAL '8 minutes'),
('P-RG-003', 1, TRUE,  'AGUARDANDO', NOW() - INTERVAL '6 minutes'),
('N-RG-001', 1, FALSE, 'AGUARDANDO', NOW() - INTERVAL '9 minutes'),
('N-CNH-001', 2, FALSE, 'AGUARDANDO', NOW() - INTERVAL '5 minutes');