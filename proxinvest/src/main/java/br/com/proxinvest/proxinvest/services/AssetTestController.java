package br.com.proxinvest.proxinvest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.services.AssetService;

@RestController
public class AssetTestController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/testar-atualizacao")
    public String testarAtualizacao() {
        assetService.atualizarValoresDosAtivos();
        return "Atualização manual dos ativos realizada com sucesso!";
    }
}