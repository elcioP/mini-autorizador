package com.miniautorizador.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.miniautorizador.miniautorizador.request.CriarCartaoRequest;
import com.miniautorizador.miniautorizador.request.TransacaoRequest;
import com.miniautorizador.miniautorizador.response.CriarCartaoResponse;
import com.miniautorizador.miniautorizador.service.CriarCartaoService;
import com.miniautorizador.miniautorizador.service.TransacaoService;
import com.miniautorizador.miniautorizador.util.TestContainer;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransacaoControllerTest extends TestContainer {

    private static final String TRANSACAO_PATH = "/transacoes";
    private MockMvc MockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setTimeZone(TimeZone.getDefault())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @InjectMocks
    private TransacaoController controller;

    @Mock
    private TransacaoService service;
    @Mock
    private CriarCartaoService cartaoService;


    @Before
    public void setup() {
        MockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(Exception.class)
                .build();
    }

    @Test
    public void testeEfetuarTransacaoComSucesso() throws Exception {
        TransacaoRequest request = TransacaoRequest.builder().numeroCartao("123").senha("123").valor(new BigDecimal(50)).build();
        when(service.validarTransacao(request)).thenReturn(new ResponseEntity(HttpStatus.OK));
        MockMvc.perform(post(TRANSACAO_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).validarTransacao(request);
    }

}