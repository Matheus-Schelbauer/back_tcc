package br.com.proxinvest.proxinvest.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.proxinvest.proxinvest.repository.AssetRepository;
import br.com.proxinvest.proxinvest.repository.WalletRepository;

@Service
public class WalletService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private WalletRepository walletRepository;

    public void atualizarValorDaWallet(Integer walletId) {
        BigDecimal total = assetRepository.calcularValorTotalDaWallet(walletId);

        walletRepository.findById(walletId).ifPresent(wallet -> {
            wallet.setWalletValue(total);// alteração aqui de wallet.setValue(total);
            walletRepository.save(wallet);
        });
    }
}
