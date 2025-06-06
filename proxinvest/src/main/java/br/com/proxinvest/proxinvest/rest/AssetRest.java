package br.com.proxinvest.proxinvest.rest;

import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.DTO.AssetDTO;
import br.com.proxinvest.proxinvest.DTO.AssetOriginalDTO;
import br.com.proxinvest.proxinvest.DTO.WalletDTO;
import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.model.AssetOriginal;
import br.com.proxinvest.proxinvest.model.User;
import br.com.proxinvest.proxinvest.model.Wallet;
import br.com.proxinvest.proxinvest.repository.AssetOriginalRepository;
import br.com.proxinvest.proxinvest.repository.AssetRepository;
import br.com.proxinvest.proxinvest.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
public class AssetRest {
    @Autowired
    private AssetRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AssetOriginalRepository assetOriginalRepository;

    @Autowired
    private WalletRepository walletRepository;

    @GetMapping(value = "/users/{userId}/wallets/{walletId}/assets", produces = "application/json;charset=UTF-8")
    public List<AssetDTO> getAssetsByWallet(
            @PathVariable("userId") Integer userId,
            @PathVariable("walletId") Integer walletId) {

        List<Asset> assets = repo.findByWalletIdWithAssetOriginal(walletId);

        if (assets == null) {
            return null;
        }

        // Atualiza os valores de cada Asset com base no AssetOriginal
        for (Asset asset : assets) {
            AssetOriginal assetOriginal = asset.getAssetOriginal();
            if (assetOriginal != null) {
                BigDecimal originalUnitary = assetOriginal.getUnitaryValue();
                Double quantity = asset.getQuantity();

                if (originalUnitary != null && quantity != null) {
                    BigDecimal calculatedTotal = originalUnitary.multiply(BigDecimal.valueOf(quantity));

                    // Só atualiza se mudou
                    if (!originalUnitary.equals(asset.getUnitaryValue())
                            || !calculatedTotal.equals(asset.getTotalValue())) {
                        asset.setUnitaryValue(originalUnitary);
                        asset.setTotalValue(calculatedTotal);
                        repo.save(asset); // salva atualização
                    }
                }
            }
        }

        // Mapeia os DTOs após atualização
        return assets.stream().map(asset -> {
            AssetDTO dto = new AssetDTO();
            dto.setId(asset.getId());
            dto.setTicketCode(asset.getTicketCode());
            dto.setQuantity(asset.getQuantity());
            dto.setUnitaryValue(asset.getUnitaryValue());
            dto.setTotalValue(asset.getTotalValue());
            dto.setWalletId(asset.getWallet().getId());

            if (asset.getAssetOriginal() != null) {
                AssetOriginalDTO originalDTO = new AssetOriginalDTO();
                originalDTO.setId(asset.getAssetOriginal().getId());
                originalDTO.setName(asset.getAssetOriginal().getName());
                originalDTO.setTicketCode(asset.getAssetOriginal().getTicketCode());
                originalDTO.setUnitaryValue(asset.getAssetOriginal().getUnitaryValue());
                dto.setAssetOriginal(originalDTO);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping(value = "/users/{userId}/wallets/{walletId}/assets", produces = "application/json;charset=UTF-8")
    public ResponseEntity<AssetDTO> createAsset(
            @PathVariable Integer userId,
            @PathVariable Integer walletId,
            @RequestBody AssetDTO assetDTO) {

        // Busca o AssetOriginal pelo ticketCode
        Optional<AssetOriginal> optionalAssetOriginal = assetOriginalRepository
                .findByTicketCode(assetDTO.getTicketCode());
        if (optionalAssetOriginal.isEmpty()) {
            // Se não encontrou o ativo original, retorna erro 400
            return ResponseEntity.badRequest().body(null);
        }
        AssetOriginal assetOriginal = optionalAssetOriginal.get();

        // Busca a Wallet pelo id (supondo que tenha WalletRepository)
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Wallet wallet = optionalWallet.get();

        // Converte DTO para Entity
        Asset asset = mapper.map(assetDTO, Asset.class);

        // Set as associações necessárias
        asset.setAssetOriginal(assetOriginal);
        asset.setWallet(wallet);

        // Define o valor unitário com base no AssetOriginal
        asset.setUnitaryValue(assetOriginal.getUnitaryValue());

        // Calcula o valor total (unitário * quantidade)
        if (asset.getQuantity() != null && asset.getUnitaryValue() != null) {
            asset.setTotalValue(asset.getUnitaryValue().multiply(BigDecimal.valueOf(asset.getQuantity())));
        }

        // Salva no banco
        repo.save(asset);

        // Retorna o DTO atualizado (com id gerado)
        AssetDTO responseDTO = mapper.map(asset, AssetDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(value = "/assets/{assetId}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Integer assetId) {

        // Busca o asset
        Optional<Asset> optionalAsset = repo.findById(assetId);

        // Verifica se existe e se pertence ao usuário certo
        if (optionalAsset.isPresent()) {
            repo.deleteById(assetId);
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    @PutMapping(value = "/users/{userId}/wallets/{walletId}/assets/{assetId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<AssetDTO> updateAsset(
            @PathVariable Integer userId,
            @PathVariable Integer walletId,
            @PathVariable Integer assetId,
            @RequestBody AssetDTO assetDTO) {

        // Tenta encontrar o asset existente
        Optional<Asset> optionalAsset = repo.findById(assetId);

        if (optionalAsset.isPresent()) {
            Asset existingAsset = optionalAsset.get();

            // Verifica se o asset pertence à wallet correta
            if (existingAsset.getWallet() != null && existingAsset.getWallet().getId() == walletId) {

                // Atualiza os campos que vieram no DTO
                existingAsset.setTicketCode(assetDTO.getTicketCode());
                existingAsset.setQuantity(assetDTO.getQuantity());
                existingAsset.setUnitaryValue(assetDTO.getUnitaryValue());
                existingAsset.setTotalValue(assetDTO.getTotalValue());

                // Salva as mudanças
                repo.save(existingAsset);

                // Retorna o DTO atualizado
                AssetDTO updatedDTO = mapper.map(existingAsset, AssetDTO.class);
                return ResponseEntity.ok(updatedDTO);
            }
        }

        return ResponseEntity.notFound().build(); // 404 se não encontrar ou não pertencer à wallet
    }
    

}
