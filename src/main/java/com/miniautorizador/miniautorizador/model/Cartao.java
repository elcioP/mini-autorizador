package com.miniautorizador.miniautorizador.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "CARTAO" , schema = "miniautorizador")
@Data
public class Cartao {
    @Column(name = "ID", nullable = false)
    @Id @GeneratedValue
    private UUID id;

    @Column(name = "NUMERO_CARTAO")
    @NonNull
    private String numeroCartao;

    @Column(name = "SENHA")
    @NonNull
    private String senha;
    @Column(name = "SALDO")
    private BigDecimal saldo;

}
