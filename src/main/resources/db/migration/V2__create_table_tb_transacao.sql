CREATE TABLE tb_transacao(
    id VARCHAR(300) PRIMARY KEY,
    valor DECIMAL(15,2),
    dataHora TIMESTAMP with time zone DEFAULT NULL,
    remetente VARCHAR(300) NOT NULL,
    destinatario VARCHAR(300) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(remetente) REFERENCES tb_users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user2 FOREIGN KEY(destinatario) REFERENCES tb_users(id) ON DELETE CASCADE
);