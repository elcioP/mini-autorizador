package com.miniautorizador.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.miniautorizador.miniautorizador.request.CriarCartaoRequest;
import com.miniautorizador.miniautorizador.response.CriarCartaoResponse;
import com.miniautorizador.miniautorizador.service.CriarCartaoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import  com.miniautorizador.miniautorizador.util.TestContainer;

public class CartaoControllerTest extends TestContainer {

    private static final String CARTAO_PATH = "/cartoes";
    private MockMvc MockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setTimeZone(TimeZone.getDefault())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @InjectMocks
    private CartaoController controller;

    @Mock
    private CriarCartaoService service;


    @Before
    public void setup() {
        MockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(Exception.class)
                .build();
    }

    @Test
    public void testePostCartaoDeveRetornarCartao() throws Exception {
        CriarCartaoRequest request = CriarCartaoRequest.builder().numeroCartao("123").senha("123").build();
        CriarCartaoResponse response = CriarCartaoResponse.builder().numeroCartao("123").senha("123").build();
        when(service.verificarStatusCartao(request)).thenReturn(new ResponseEntity(response, HttpStatus.CREATED));

        MockMvc.perform(post(CARTAO_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.numeroCartao").value(response.getNumeroCartao()),
                        jsonPath("$.senha").value(response.getSenha())
                )
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(service, times(1)).verificarStatusCartao(request);
    }

    @Test
    public void testeVerificarSaldoCartao() throws Exception {
        String numeroCartao = "123";
        BigDecimal response = new BigDecimal(500.0);
        when(service.verificarCartao(anyString())).thenReturn(new ResponseEntity(response, HttpStatus.OK));
        MockMvc.perform(get(CARTAO_PATH.concat("/{numeroCartao}") , "numeroCartao", numeroCartao)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).verificarCartao(anyString());
    }
}