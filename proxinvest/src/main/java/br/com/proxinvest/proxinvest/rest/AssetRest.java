package br.com.proxinvest.proxinvest.rest;

import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.DTO.AssetDTO;
import br.com.proxinvest.proxinvest.DTO.WalletDTO;
import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.model.User;
import br.com.proxinvest.proxinvest.model.Wallet;
import br.com.proxinvest.proxinvest.repository.AssetRepository;

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

    @GetMapping(value = "/users/{userId}/wallets/{walletId}/assets", produces = "application/json;charset=UTF-8")
    public List<AssetDTO> getAssetsByWallet(
            @PathVariable("userId") Integer userId,
            @PathVariable("walletId") Integer walletId) {
        List<Asset> assets = repo.findByWallet_Id(walletId);

        if (assets != null) {
            return assets.stream().map(e -> mapper.map(e, AssetDTO.class)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @PostMapping(value = "/users/{userId}/wallets/{walletId}/assets", produces = "application/json;charset=UTF-8")
    public ResponseEntity<AssetDTO> createAsset(
            @PathVariable Integer userId,
            @PathVariable Integer walletId,
            @RequestBody AssetDTO asset) {

        // Converte o DTO para entidade Asset
        Asset a = mapper.map(asset, Asset.class);

        // Salva o asset
        repo.save(a);

        return ResponseEntity.ok(asset);

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
