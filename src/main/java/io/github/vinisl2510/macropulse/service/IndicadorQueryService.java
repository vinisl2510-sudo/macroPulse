package io.github.vinisl2510.macropulse.service;

import io.github.vinisl2510.macropulse.dto.response.CotacaoDTO;
import io.github.vinisl2510.macropulse.exception.DadosNaoEncontradosException;
import io.github.vinisl2510.macropulse.model.TipoIndicador;
import io.github.vinisl2510.macropulse.repository.IndicadorRepository;
import org.springframework.stereotype.Service;

@Service
public class IndicadorQueryService {
    private final IndicadorRepository indicadorRepository;

    public IndicadorQueryService(IndicadorRepository indicadorRepository) {
        this.indicadorRepository = indicadorRepository;
    }

    public CotacaoDTO selicAtual(){
       return indicadorRepository.findTopByTipoOrderByDataDesc(TipoIndicador.SELIC)
                .map(ultimo -> new CotacaoDTO(ultimo.getData(), ultimo.getValor()))
                .orElseThrow(() -> new DadosNaoEncontradosException("Nenhum valor da Selic encontrado"));

    }
}
