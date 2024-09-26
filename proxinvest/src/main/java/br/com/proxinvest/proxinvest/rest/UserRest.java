package br.com.proxinvest.proxinvest.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.DTO.UserDTO;
import br.com.proxinvest.proxinvest.DTO.WalletDTO;
import br.com.proxinvest.proxinvest.model.User;
import br.com.proxinvest.proxinvest.repository.UserRepository;

@CrossOrigin
@RestController
public class UserRest {
    @Autowired
    private UserRepository repo;
    
    @Autowired
    private ModelMapper mapper;
        //Como fazer?
        @GetMapping(value = "/user/{id}", produces = "application/json;charset=UTF-8")
            public List<UserDTO> getById(@PathVariable("id") Integer id){
        // List<Wallet> wallets = repo.findByUser_id(id);
        List<User> user = repo.findAll();

        if (user != null){
            return user.stream().map(e -> mapper.map(e,UserDTO.class)).collect(Collectors.toList());
        }else{
            return null;
        }
    }
}