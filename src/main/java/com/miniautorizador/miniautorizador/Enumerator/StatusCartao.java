package com.miniautorizador.miniautorizador.Enumerator;

import lombok.Getter;

@Getter
public enum StatusCartao {
    CARTAO_EXISTE("cartão já cadastrado"),
    CARTAO_INEXISTENTE("cartão inexistente");

    private String status;
    StatusCartao(String status){
        this.status = status;
    }

}
