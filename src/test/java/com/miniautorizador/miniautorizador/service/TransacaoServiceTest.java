package com.miniautorizador.miniautorizador.service;

import com.miniautorizador.miniautorizador.model.Cartao;
import com.miniautorizador.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.miniautorizador.request.TransacaoRequest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import  com.miniautorizador.miniautorizador.util.TestContainer;

import java.math.BigDecimal;

public class TransacaoServiceTest extends TestContainer{

    @InjectMocks
    private TransacaoService service;

    @Mock CriarCartaoService cartaoService;

    @Test
    public void efetuarTransacao() {
        TransacaoRequest request = TransacaoRequest.builder().numeroCartao("123").senha("123").valor(new BigDecimal(50)).build();
        Cartao cartaoBanco = new Cartao();
        cartaoBanco.setNumeroCartao("123");
        cartaoBanco.setSenha("123");
        cartaoBanco.setSaldo(new BigDecimal(500));

        when(cartaoService.verificarExistenciaCartao(request.getNumeroCartao())).thenReturn(true);
        when(cartaoService.buscarCartao(request.getNumeroCartao())).thenReturn(cartaoBanco);
        ResponseEntity<?> response =  service.validarTransacao(request);

        Assert.assertEquals(response.getStatusCode() , HttpStatus.OK);

    }
}
