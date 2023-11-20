package com.miniautorizador.miniautorizador.repository;

import com.miniautorizador.miniautorizador.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public  interface CartaoRepository extends JpaRepository<Cartao, UUID> {
    boolean existsByNumeroCartao(String numeroCartao);
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
