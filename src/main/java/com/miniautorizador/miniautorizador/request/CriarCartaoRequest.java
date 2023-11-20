package com.miniautorizador.miniautorizador.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarCartaoRequest {
    @NotBlank(message = "número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "senha do cartão é obrigatório")
    private String senha;

}
