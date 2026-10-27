package io.github.vinisl2510.macropulse.client;

import io.github.vinisl2510.macropulse.dto.bcb.SelicBcbDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SelicClient {
    private final RestClient restClient;
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public SelicClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<SelicBcbDTO> buscarPorPeriodo (LocalDate inicio,LocalDate fim) {
        return restClient.get()
                .uri("https://api.bcb.gov.br/dados/serie/bcdata.sgs.432/dados?formato=json&dataInicial={inicio}&dataFinal={fim}"
                        , DATA.format(inicio), DATA.format(fim))
                .retrieve()
                .body(new ParameterizedTypeReference<List<SelicBcbDTO>>() {}) ;
    }
}
