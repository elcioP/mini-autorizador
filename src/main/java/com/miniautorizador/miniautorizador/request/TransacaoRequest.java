package com.miniautorizador.miniautorizador.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequest {

    @NotBlank(message = "número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "senha do cartão é obrigatório")
    private String senha;

     @NotNull(message = "valor da transação é obrigatório")
    private BigDecimal valor;
}
