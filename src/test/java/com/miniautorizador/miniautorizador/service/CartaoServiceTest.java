package com.miniautorizador.miniautorizador.service;

import com.miniautorizador.miniautorizador.Enumerator.ValorInicialCartaoEnum;
import com.miniautorizador.miniautorizador.model.Cartao;
import com.miniautorizador.miniautorizador.repository.CartaoRepository;
import com.miniautorizador.miniautorizador.request.CriarCartaoRequest;
import com.miniautorizador.miniautorizador.request.TransacaoRequest;
import com.miniautorizador.miniautorizador.response.CriarCartaoResponse;
import com.miniautorizador.miniautorizador.util.TestContainer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class CartaoServiceTest extends TestContainer{

    @InjectMocks
    private CriarCartaoService service;

    @Mock
    private CartaoRepository repository;

    @Test
    public void efetuarTransacao() throws Exception {
        CriarCartaoRequest request = CriarCartaoRequest.builder().numeroCartao("123").senha("123").build();
        CriarCartaoResponse responseCartao = CriarCartaoResponse.builder().numeroCartao("123").senha("123").build();
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(request.getNumeroCartao());
        cartao.setSenha(request.getSenha());
        when(repository.existsByNumeroCartao(request.getNumeroCartao())).thenReturn(false);
        when(repository.save(Mockito.any())).thenReturn(cartao);

        ResponseEntity<?> response = service.verificarStatusCartao(request);
        Assert.assertEquals(response.getStatusCode() , HttpStatus.CREATED);
        Assert.assertEquals(response.getBody() , responseCartao);

    }
}
