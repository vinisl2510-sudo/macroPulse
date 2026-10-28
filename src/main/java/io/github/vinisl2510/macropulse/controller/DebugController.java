package io.github.vinisl2510.macropulse.controller;

import io.github.vinisl2510.macropulse.client.SelicClient;
import io.github.vinisl2510.macropulse.dto.bcb.SelicBcbDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/debug")
public class DebugController {
    private final SelicClient selicClient;

    public DebugController(SelicClient selicClient) {
        this.selicClient = selicClient;
    }

    @GetMapping("/selic")
    public List<SelicBcbDTO>buscarPorPeriodo(){
        return selicClient.buscarPorPeriodo(LocalDate.now()
                .minusDays(30),
                LocalDate.now());
    }

}
