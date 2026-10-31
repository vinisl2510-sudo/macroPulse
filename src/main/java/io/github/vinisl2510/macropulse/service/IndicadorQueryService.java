package io.github.vinisl2510.macropulse.service;

import io.github.vinisl2510.macropulse.dto.response.CotacaoDTO;
import io.github.vinisl2510.macropulse.exception.DadosNaoEncontradosException;
import io.github.vinisl2510.macropulse.model.TipoIndicador;
import io.github.vinisl2510.macropulse.repository.IndicadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public CotacaoDTO dolarAtual(){
        return indicadorRepository.findTopByTipoOrderByDataDesc(TipoIndicador.DOLAR)
                .map(ultimo -> new CotacaoDTO(ultimo.getData(), ultimo.getValor()))
                .orElseThrow(() -> new DadosNaoEncontradosException("Nenhum valor da dolar encontrado"));
    }

    public List<CotacaoDTO> selicHistorico(LocalDate inicio, LocalDate fim) {
        return indicadorRepository.findByTipoAndDataBetweenOrderByDataAsc(TipoIndicador.SELIC, inicio, fim)
                .stream()
                .map(selic -> new CotacaoDTO(selic.getData(), selic.getValor()))
                .toList();
    }

        public List<CotacaoDTO> dolarHistorico(LocalDate inicio, LocalDate fim){
        return indicadorRepository.findByTipoAndDataBetweenOrderByDataAsc(TipoIndicador.DOLAR, inicio, fim)
                .stream()
                .map(dolar -> new CotacaoDTO(dolar.getData(), dolar.getValor()))
                .toList();
    }
}
