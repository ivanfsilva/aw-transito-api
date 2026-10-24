CREATE TABLE veiculo (
 id BIGINT NOT NULL AUTO_INCREMENT,
 proprietario_id BIGINT NOT NULL,
 marca VARCHAR(20) NOT NULL,
 modelo VARCHAR(20) NOT NULL,
 placa VARCHAR(7) NOT NULL,
 status VARCHAR(20) NOT NULL,
 data_cadastro DATETIME NOT NULL,
 data_apreensao DATETIME,

 CONSTRAINT pk_veiculo PRIMARY KEY (id),
 CONSTRAINT uk_veiculo_placa UNIQUE (placa),
 CONSTRAINT fk_veiculo_proprietario FOREIGN KEY (proprietario_id) REFERENCES proprietario (id)
);