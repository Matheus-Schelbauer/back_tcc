package br.com.proxinvest.proxinvest.rest;

import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.DTO.AssetDTO;
import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.repository.AssetRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin
@RestController
public class AssetRest {
    @Autowired
    private AssetRepository repo;
    
    @Autowired
    private ModelMapper mapper;
        @GetMapping(value = "/asset/{id}", produces = "application/json;charset=UTF-8")
            public List<AssetDTO> getById(@PathVariable("id") Integer id){
        // List<Asset> assets = repo.findByWallet_Id(id); //MUDAR PARA WALLET ID
        List<Asset> assets = repo.findAll();

        if (assets != null){
            return assets.stream().map(e -> mapper.map(e,AssetDTO.class)).collect(Collectors.toList());
        }else{
            return null;
        }
    }
}
