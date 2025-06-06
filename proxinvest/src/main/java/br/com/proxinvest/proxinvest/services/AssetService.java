package br.com.proxinvest.proxinvest.services;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.model.AssetOriginal;
import br.com.proxinvest.proxinvest.repository.AssetRepository;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private WalletService walletService;

    public void atualizarValoresDosAtivos() {
        List<Asset> ativos = assetRepository.findAll();
        Set<Integer> walletIds = new HashSet<>();

        for (Asset ativo : ativos) {
            AssetOriginal original = ativo.getAssetOriginal();
            if (original != null) {
                BigDecimal quantidade = BigDecimal.valueOf(ativo.getQuantity());
                BigDecimal valorUnitario = original.getUnitaryValue();
                BigDecimal valorTotal = quantidade.multiply(valorUnitario);

                ativo.setUnitaryValue(valorUnitario);
                ativo.setTotalValue(valorTotal);

                // Guarda o ID da carteira para atualizar depois
                walletIds.add(ativo.getWallet().getId());

                // System.out.println(
                //         "Ticket: " + ativo.getTicketCode() +
                //                 " | Nome: " + original.getName() +
                //                 " | Quantidade: " + quantidade +
                //                 " | Valor Unitário: " + valorUnitario +
                //                 " | Valor Total: " + valorTotal);
            }
        }

        assetRepository.saveAll(ativos);

        // Atualiza os valores das carteiras relacionadas
        for (Integer walletId : walletIds) {
            walletService.atualizarValorDaWallet(walletId);
        }
    }

    // <<< Método que você precisa adicionar no AssetService para o controller
    // funcionar >>>
    public List<Asset> buscarTodosAtivos() {
        return assetRepository.findAll();
    }
}
