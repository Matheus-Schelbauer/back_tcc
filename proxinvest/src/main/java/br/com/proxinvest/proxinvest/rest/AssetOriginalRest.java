package br.com.proxinvest.proxinvest.rest;

import br.com.proxinvest.proxinvest.DTO.AssetOriginalDTO;
import br.com.proxinvest.proxinvest.model.AssetOriginal;
import br.com.proxinvest.proxinvest.repository.AssetOriginalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market/assets-original")
public class AssetOriginalRest {

    private final AssetOriginalRepository assetOriginalRepository;

    public AssetOriginalRest(AssetOriginalRepository assetOriginalRepository) {
        this.assetOriginalRepository = assetOriginalRepository;
    }

    // Buscar todos os ativos disponíveis na B3
    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<AssetOriginal> getAllAssetsOriginal() {
        return assetOriginalRepository.findAll();
    }

    // Buscar um ativo original pelo ticket code
    @GetMapping(value = "/{ticketCode}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<AssetOriginal> getAssetOriginalByTicket(@PathVariable String ticketCode) {
        return assetOriginalRepository.findByTicketCode(ticketCode)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}