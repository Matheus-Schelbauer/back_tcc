package br.com.proxinvest.proxinvest.rest;

import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.DTO.UserDTO;
import br.com.proxinvest.proxinvest.DTO.WalletDTO;
import br.com.proxinvest.proxinvest.model.User;
import br.com.proxinvest.proxinvest.model.Wallet;
import br.com.proxinvest.proxinvest.repository.WalletRepository;

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
public class WalletRest {
    @Autowired
    private WalletRepository repo;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/users/{userId}/wallets", produces = "application/json;charset=UTF-8")
    public List<WalletDTO> getById(@PathVariable("userId") Integer id) {
        List<Wallet> wallets = repo.findByUser_id(id);
        // List<Wallet> wallets = repo.findAll();

        if (wallets != null) {
            return wallets.stream().map(e -> mapper.map(e, WalletDTO.class)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @PostMapping(value = "/users/{userId}/wallets", produces = "application/json;charset=UTF-8")
    public WalletDTO inserir(@PathVariable Integer userId, @RequestBody WalletDTO wallet) {
        // salva a Entidade convertida do DTO
        Wallet w = mapper.map(wallet, Wallet.class);

        // Cria um User fake só com o ID, sem precisar do front mandar
        User user = new User();
        user.setId(userId);
        w.setUser(user); // associa a carteira ao usuário correto

        repo.save(w);
        // busca a carteira/wallet inserido
        w = repo.findById(w.getId());
        // retorna o DTO equivalente à entidade
        return mapper.map(w, WalletDTO.class);
    }
    
    @DeleteMapping(value = "/users/{userId}/wallets/{walletId}")
    public ResponseEntity<Void> deleteWallet(
            @PathVariable Integer userId,
            @PathVariable Integer walletId) {

        // Busca a wallet
        Optional<Wallet> optionalWallet = repo.findById(walletId);

        // Verifica se existe e se pertence ao usuário certo
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get(); // Pegando o Wallet real de dentro do Optional

            if (wallet.getUser() != null && wallet.getUser().getId() == userId) {
                repo.deleteById(walletId);
                return ResponseEntity.noContent().build(); // 204 No Content
            }
        }

        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    @PutMapping(value = "/users/{userId}/wallets/{walletId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<WalletDTO> updateWallet(
            @PathVariable Integer userId,
            @PathVariable Integer walletId,
            @RequestBody WalletDTO updatedWalletDTO) {

        Optional<Wallet> optionalWallet = repo.findById(walletId);

        // Verifica se a carteira existe
        if (!optionalWallet.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Wallet wallet = optionalWallet.get();

        // Verifica se pertence ao usuário certo
        if (wallet.getUser() == null || wallet.getUser().getId() != userId) {
            return ResponseEntity.status(403).build(); // 403 Forbidden
        }

        // Atualiza os campos
        wallet.setName(updatedWalletDTO.getName());
        wallet.setDescription(updatedWalletDTO.getDescription());
        wallet.setWalletValue(updatedWalletDTO.getWalletValue());

        // Salva a carteira atualizada
        repo.save(wallet);

        // Retorna a nova versão como DTO
        WalletDTO result = mapper.map(wallet, WalletDTO.class);
        return ResponseEntity.ok(result); // 200 OK
    }

}
