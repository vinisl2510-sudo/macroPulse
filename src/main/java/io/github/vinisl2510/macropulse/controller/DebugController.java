package io.github.vinisl2510.macropulse.controller;

import io.github.vinisl2510.macropulse.client.PtaxClient;
import io.github.vinisl2510.macropulse.client.SelicClient;
import io.github.vinisl2510.macropulse.dto.bcb.PtaxBcbDTO;
import io.github.vinisl2510.macropulse.dto.bcb.PtaxRespostaBcbDTO;
import io.github.vinisl2510.macropulse.dto.bcb.SelicBcbDTO;
import io.github.vinisl2510.macropulse.service.AtualizacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/debug")
public class DebugController {
    private final SelicClient selicClient;
    private final PtaxClient ptaxClient;
    private final AtualizacaoService service;

    public DebugController(SelicClient selicClient, PtaxClient ptaxClient, AtualizacaoService service) {
        this.selicClient = selicClient;
        this.ptaxClient = ptaxClient;
        this.service = service;
    }

    @GetMapping("/selic")
    public List<SelicBcbDTO>buscarPorPeriodo(){
        return selicClient.buscarPorPeriodo(LocalDate.now()
                .minusDays(30),
                LocalDate.now());
    }

    @GetMapping("/dolar")
    public List<PtaxBcbDTO>cotacaoPeriodoDolar(){
        return ptaxClient.cotacaoDolarPeriodo(LocalDate.now().minusDays(30), LocalDate.now());
    }

    @GetMapping("/carregar-selic")
    public String carregarSelic(){
        service.atualizarSelic();
        return "atualizado";
    }

}
