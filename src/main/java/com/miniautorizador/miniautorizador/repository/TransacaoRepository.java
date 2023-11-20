package com.miniautorizador.miniautorizador.repository;

import com.miniautorizador.miniautorizador.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public  interface TransacaoRepository extends JpaRepository<Cartao, UUID> {
    BigDecimal findCartaoSaldoByNumeroCartao(String numeroCartao);
}
