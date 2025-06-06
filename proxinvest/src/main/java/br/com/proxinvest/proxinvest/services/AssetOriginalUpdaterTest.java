package br.com.proxinvest.proxinvest.services;

import br.com.proxinvest.proxinvest.services.BrApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market/update")
public class AssetOriginalUpdaterTest {

    @Autowired
    private BrApiService brApiService;

    @PostMapping
    public ResponseEntity<String> atualizarCotacoes() {
        brApiService.getCotacoesAcoes();
        return ResponseEntity.ok("Cotações atualizadas com sucesso.");
    }
}
