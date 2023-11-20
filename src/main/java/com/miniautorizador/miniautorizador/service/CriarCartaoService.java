package com.miniautorizador.miniautorizador.service;

import com.miniautorizador.miniautorizador.Enumerator.StatusCartao;
import com.miniautorizador.miniautorizador.Enumerator.ValorInicialCartaoEnum;
import com.miniautorizador.miniautorizador.model.Cartao;
import com.miniautorizador.miniautorizador.request.CriarCartaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.miniautorizador.miniautorizador.repository.CartaoRepository;

import java.math.BigDecimal;

@Service
public class CriarCartaoService {
    @Autowired
    private CartaoRepository criarCartaoRepository;


    // o método abaixo chama o método que verifica a existência do cartão, se não existir chama o método de criar
    public ResponseEntity<?>  verificarStatusCartao(CriarCartaoRequest request)  throws Exception{
        ResponseEntity response = (this.verificarExistenciaCartao(request.getNumeroCartao())) ? this.cartaoExiste() : this.criarCartao(request);
        return response;
    }
    public Boolean verificarExistenciaCartao(String numeroCartao){
        return criarCartaoRepository.existsByNumeroCartao(numeroCartao);
    }

    // o método abaixo cria novo cartão com o saldo de quinhento reais e chama o método salvar
    private ResponseEntity<?> criarCartao(CriarCartaoRequest request) throws Exception {
        try {
            Cartao cartaoNovo = new Cartao();
            cartaoNovo.setNumeroCartao(request.getNumeroCartao());
            cartaoNovo.setSenha(request.getSenha());
            cartaoNovo.setSaldo(ValorInicialCartaoEnum.VALOR_INICIAL.getValorInicial());
            Cartao cartaoSalvo =  this.salvarCartao(cartaoNovo);
            return new ResponseEntity(cartaoSalvo, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
    public ResponseEntity<?> varificarCartao(String numeroCartao) throws Exception {
        ResponseEntity response = (criarCartaoRepository.existsByNumeroCartao(numeroCartao)) ? this.obterSaldo(numeroCartao) : this.cartaoNaoExiste();
        return response;
    }

    // o método abaixo verifica se o cartão existe, se existir retorno o saldo do cartão
    private ResponseEntity<?> obterSaldo(String numeroCartao) throws Exception {
        try {
            Cartao cartaoComSaldo = this.buscarCartao(numeroCartao);
            return new ResponseEntity(cartaoComSaldo.getSaldo(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
    // método para buscar o cartao do banco de dados atraves do repository
    public Cartao buscarCartao(String numeroCartao){
        return criarCartaoRepository.findByNumeroCartao(numeroCartao).orElseThrow();
    }
    // método que retorna que o cartão existe
    private ResponseEntity<?> cartaoExiste() {
        return new ResponseEntity(StatusCartao.CARTAO_EXISTE.getStatus(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    // método que retorna que o cartão não existe
    public ResponseEntity<?>  cartaoNaoExiste() {
        return new ResponseEntity(StatusCartao.CARTAO_INEXISTENTE.getStatus(), HttpStatus.NOT_FOUND);
    }
    // o método abaixo salva o cartão
    public Cartao salvarCartao(Cartao cartao) {
        return criarCartaoRepository.save(cartao);
    }
}
