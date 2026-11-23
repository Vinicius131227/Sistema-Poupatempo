CREATE TABLE servico(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE guiche(
    id SERIAL PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    ocupado BOOLEAN NOT NULL DEFAULT FALSE,
    contador_prioridade_consecutiva INT NOT NULL DEFAULT 0
);

CREATE TABLE guiche_servico(
    guiche_id INT NOT NULL REFERENCES guiche(id) ON DELETE CASCADE,
    servico_id INT NOT NULL REFERENCES servico(id) ON DELETE CASCADE,
    PRIMARY KEY (guiche_id, servico_id)
);

CREATE TABLE senha(
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL,
    servico_id INT NOT NULL REFERENCES servico(id),
    guiche_id INT NULL REFERENCES guiche(id),
    prioritaria BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(20) NOT NULL DEFAULT 'AGUARDANDO',
    rechamadas INT NOT NULL DEFAULT 0,
    dt_emissao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dt_chamada TIMESTAMP NULL,
    dt_inicio TIMESTAMP NULL,
    dt_termino TIMESTAMP NULL
);