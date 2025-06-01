package br.com.proxinvest.proxinvest.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.model.AssetOriginal;
import br.com.proxinvest.proxinvest.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public void atualizarValoresDosAtivos() {
        List<Asset> ativos = assetRepository.findAll();
        for (Asset ativo : ativos) {
            AssetOriginal original = ativo.getAssetOriginal();
            if (original != null) {
                System.out.println("Ativo: " + ativo.getTicketCode() +
                        " | Valor Original: " + original.getUnitaryValue() +
                        " | Quantidade: " + ativo.getQuantity());

                ativo.setUnitaryValue(original.getUnitaryValue());
                ativo.setTotalValue(ativo.getQuantity() * original.getUnitaryValue());
            }
        }
        assetRepository.saveAll(ativos);
    }
}