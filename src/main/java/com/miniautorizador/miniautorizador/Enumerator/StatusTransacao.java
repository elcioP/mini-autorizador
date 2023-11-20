package com.miniautorizador.miniautorizador.Enumerator;

import lombok.Getter;

@Getter
public enum StatusTransacao {
    SALDO_INSUFICIENTE("saldo insuficiente para essa transação"),
    SENHA_INVALIDA("senha inválido");

    private String status;
    StatusTransacao(String status){
        this.status = status;
    }

}
