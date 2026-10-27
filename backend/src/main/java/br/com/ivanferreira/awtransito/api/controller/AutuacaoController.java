package br.com.ivanferreira.awtransito.api.controller;

import br.com.ivanferreira.awtransito.api.assembler.AutuacaoAssembler;
import br.com.ivanferreira.awtransito.api.model.AutuacaoModel;
import br.com.ivanferreira.awtransito.api.model.input.AutuacaoInput;
import br.com.ivanferreira.awtransito.domain.model.Autuacao;
import br.com.ivanferreira.awtransito.domain.model.Veiculo;
import br.com.ivanferreira.awtransito.domain.service.RegistroAutuacaoService;
import br.com.ivanferreira.awtransito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos/{veiculoId}/autuacoes")
public class AutuacaoController {

    private final AutuacaoAssembler autuacaoAssembler;
    private final RegistroAutuacaoService registroAutuacaoService;
    private final RegistroVeiculoService registroVeiculoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutuacaoModel registrar(@PathVariable Long veiculoId,
                                   @Valid @RequestBody AutuacaoInput autuacaoInput) {
        Autuacao novaAutuacao = autuacaoAssembler.toEntity(autuacaoInput);
        Autuacao autuacaoRegistrada = registroAutuacaoService
                .registrar(veiculoId, novaAutuacao);
        return autuacaoAssembler.toModel(autuacaoRegistrada);
    }

    @GetMapping
    public List<AutuacaoModel> listar(@PathVariable Long veiculoId) {
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId);
        return autuacaoAssembler.toCollectionModel(veiculo.getAutuacoes());
    }

}
