package io.github.vinisl2510.macropulse.service;

import io.github.vinisl2510.macropulse.client.SelicClient;
import io.github.vinisl2510.macropulse.dto.bcb.SelicBcbDTO;
import io.github.vinisl2510.macropulse.model.Indicador;
import io.github.vinisl2510.macropulse.model.TipoIndicador;
import io.github.vinisl2510.macropulse.repository.IndicadorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AtualizacaoService {
    private final IndicadorRepository repository;
    private final SelicClient client;
    private static final DateTimeFormatter FORMATO_SGS = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AtualizacaoService(IndicadorRepository repository,
                              SelicClient client) {
        this.repository = repository;
        this.client = client;
    }

    public void atualizarSelic() {
        LocalDate inicio = repository.findTopByTipoOrderByDataDesc(TipoIndicador.SELIC)
                .map(ultimo -> ultimo.getData().plusDays(1))
                .orElse(LocalDate.now().minusYears(2));
        LocalDate hoje = LocalDate.now();
        if(inicio.isAfter(hoje)){
            return;
        }
        List<SelicBcbDTO> resposta = client.buscarPorPeriodo(inicio,hoje);

        List<Indicador> novos = new ArrayList<>();
        for (SelicBcbDTO dto : resposta){
            LocalDate data = LocalDate.parse(dto.data(), FORMATO_SGS);
            if (data.isAfter(hoje) || repository.existsByDataAndTipo(data,TipoIndicador.SELIC)){
                continue;
            }
            BigDecimal valor = new BigDecimal(dto.valor());

            Indicador indicador = new Indicador(TipoIndicador.SELIC, data, valor);
            novos.add(indicador);

        }

        repository.saveAll(novos);
    }
}
