package br.com.ivanferreira.awtransito.api.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    private Proprietario proprietario;
    private String marca;
    private String modelo;
    private String placa;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.AUTO)
    private StatusVeiculo status;

    @JsonProperty(access = JsonProperty.Access.AUTO)
    private LocalDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.AUTO)
    private LocalDateTime dataApreensao;

}
