package com.miniautorizador.miniautorizador.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarCartaoResponse {

    private String numeroCartao;
    private String senha;
}
