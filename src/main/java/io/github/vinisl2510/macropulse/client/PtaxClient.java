package io.github.vinisl2510.macropulse.client;

import io.github.vinisl2510.macropulse.dto.bcb.PtaxBcbDTO;
import io.github.vinisl2510.macropulse.dto.bcb.PtaxRespostaBcbDTO;
import io.github.vinisl2510.macropulse.dto.bcb.SelicBcbDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PtaxClient {
    private final RestClient restClient;
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public PtaxClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<PtaxBcbDTO> cotacaoDolarPeriodo (LocalDate inicio, LocalDate fim) {
        PtaxRespostaBcbDTO resposta = restClient.get()
                .uri("https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)?@dataInicial='{inicio}'&@dataFinalCotacao='{fim}'&$format=json"
                        , DATA.format(inicio), DATA.format(fim))
                .retrieve()
                .body(PtaxRespostaBcbDTO.class);

        return resposta.value();
    }

    }
