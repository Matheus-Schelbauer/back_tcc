package br.com.proxinvest.proxinvest.rest;

import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.DTO.WalletDTO;
import br.com.proxinvest.proxinvest.model.Wallet;
import br.com.proxinvest.proxinvest.repository.WalletRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin
@RestController
public class WalletRest {
    @Autowired
    private WalletRepository repo;
    
    @Autowired
    private ModelMapper mapper;
        @GetMapping(value = "/wallet/{id}", produces = "application/json;charset=UTF-8")
            public List<WalletDTO> getById(@PathVariable("id") Integer id){
        List<Wallet> wallets = repo.findByUser_id(id);
        // List<Wallet> wallets = repo.findAll();

        if (wallets != null){
            return wallets.stream().map(e -> mapper.map(e,WalletDTO.class)).collect(Collectors.toList());
        }else{
            return null;
        }
    }
}
