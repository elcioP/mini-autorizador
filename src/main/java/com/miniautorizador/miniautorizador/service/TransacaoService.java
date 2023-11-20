package com.miniautorizador.miniautorizador.service;

import com.miniautorizador.miniautorizador.Enumerator.StatusCartao;
import com.miniautorizador.miniautorizador.Enumerator.StatusTransacao;
import com.miniautorizador.miniautorizador.model.Cartao;
import com.miniautorizador.miniautorizador.repository.TransacaoRepository;
import com.miniautorizador.miniautorizador.request.TransacaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class TransacaoService {
    @Autowired
    private CriarCartaoService cartaoService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    private Cartao cartao;

    public ResponseEntity<?> validarTransacao(TransacaoRequest transacaoRequest) {
        Boolean cartaoExiste = this.verificarExistenciaCartao(transacaoRequest);
        //nessa parte do código necessitei utilizar if
        if (!cartaoExiste) {
            return cartaoService.cartaoNaoExiste();
        } else if (!this.verificarSenha(transacaoRequest)) {
            return this.senhaInvalida();
        } else if (!this.validarSaldo(transacaoRequest)) {
           return this.saldoInvalido();
        } else {
            return this.efetuarTransacao(transacaoRequest);
        }
    }

    public ResponseEntity<?> efetuarTransacao(TransacaoRequest transacaoRequest) {
        Cartao cartao = this.buscarCartao(transacaoRequest);
        BigDecimal valorSaldoAtual = cartao.getSaldo().subtract(transacaoRequest.getValor());
        cartao.setSaldo(valorSaldoAtual);
        cartaoService.salvarCartao(cartao);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<?> saldoInvalido() {
        return new ResponseEntity(StatusTransacao.SALDO_INSUFICIENTE.getStatus(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> senhaInvalida() {
        return new ResponseEntity(StatusTransacao.SENHA_INVALIDA.getStatus(), HttpStatus.NOT_FOUND);
    }

    // o método abaixo verifica a existência do cartão
    private Boolean verificarExistenciaCartao(TransacaoRequest transacaoRequest) {
        Boolean cartaoValido = cartaoService.verificarExistenciaCartao(transacaoRequest.getNumeroCartao());
        return cartaoValido;
    }

    // o método abaixo verifica se a senha é valida
    private Boolean verificarSenha(TransacaoRequest transacaoRequest) {
        Cartao cartaoRecuperadoDoBancoDeDados = this.buscarCartao(transacaoRequest);
        Boolean senhaValida = cartaoRecuperadoDoBancoDeDados.getSenha().equals(transacaoRequest.getSenha());
        return senhaValida;
    }

    // o método abaixo verifica se o cartão tem saldo para transação
    private Boolean validarSaldo(TransacaoRequest transacaoRequest) {
        BigDecimal saldoCartaoRecuperado = this.buscarCartao(transacaoRequest).getSaldo();
        int retornoSaldo = saldoCartaoRecuperado.compareTo(transacaoRequest.getValor());
        return (retornoSaldo == 1) ? true : false;
    }

    // nesse método foi utilizado o padrão de projeto singleton para verificar se o cartao ja teria sido buscado no banco de dados
    // caso ja estivesse preenchido, não buscaria no banco
    private Cartao buscarCartao(TransacaoRequest transacaoRequest) {
        return (Objects.isNull(this.cartao)) ? cartaoService.buscarCartao(transacaoRequest.getNumeroCartao()) : cartao;
    }
}
