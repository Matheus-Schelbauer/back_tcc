package br.com.proxinvest.proxinvest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.model.AssetOriginal;
import br.com.proxinvest.proxinvest.services.AssetService;

@RestController
public class AssetTestController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/testar-atualizacao")
    public String testarAtualizacao() {
        assetService.atualizarValoresDosAtivos();

        List<Asset> ativos = assetService.buscarTodosAtivos();

        StringBuilder sb = new StringBuilder();
        for (Asset ativo : ativos) {
            AssetOriginal original = ativo.getAssetOriginal();

            sb.append("Ativo: ").append(ativo.getTicketCode()).append("\n")
                    .append(" - Quantidade: ").append(ativo.getQuantity()).append("\n")
                    .append(" - Valor Unitário (Asset): ").append(ativo.getUnitaryValue()).append("\n")
                    .append(" - Valor Total: ").append(ativo.getTotalValue()).append("\n");

            if (original != null) {
                sb.append(" - Baseado em AssetOriginal:").append("\n")
                        .append("     - Nome: ").append(original.getName()).append("\n")
                        .append("     - Valor de Referência: ").append(original.getUnitaryValue()).append("\n");
            }

            sb.append("\n");
        }
        return sb.toString();
    }

}