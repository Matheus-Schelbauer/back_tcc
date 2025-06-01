package br.com.proxinvest.proxinvest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // <--- Isso aqui é ESSENCIAL
public class AssetUpdateScheduler {

    @Autowired
    private AssetService assetService;

    @Scheduled(fixedRate = 3600000) // Executa a cada 1 hora
    public void atualizarAtivosPeriodicamente() {
        System.out.println("Atualizando ativos...");
        assetService.atualizarValoresDosAtivos();
    }
}
