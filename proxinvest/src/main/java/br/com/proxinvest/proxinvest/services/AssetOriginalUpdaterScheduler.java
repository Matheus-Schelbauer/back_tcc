
package br.com.proxinvest.proxinvest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // <--- Isso aqui é ESSENCIAL
public class AssetOriginalUpdaterScheduler {

    // @Autowired
    // private AssetService assetService;

    @Autowired
    private BrApiService brApiService;

    @Scheduled(fixedRate = 21600000) // 6 horas = 6 * 60 * 60 * 1000 = 21600000 ms
    public void atualizarAtivosPeriodicamente() {
        // System.out.println("🔄 Atualizando ativos e cotação da Brapi...");

        // Atualiza as cotações diretamente da Brapi
        brApiService.getCotacoesAcoes();

        // // Atualiza os ativos com os novos preços
        // assetService.atualizarValoresDosAtivos();
    }
}
