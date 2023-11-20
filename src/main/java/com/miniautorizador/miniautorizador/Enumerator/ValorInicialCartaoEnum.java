package com.miniautorizador.miniautorizador.Enumerator;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum ValorInicialCartaoEnum {
    VALOR_INICIAL(new BigDecimal(500.00));
   private BigDecimal valorInicial;
    ValorInicialCartaoEnum(BigDecimal valorInicial){
        this.valorInicial = valorInicial;

   }
}
