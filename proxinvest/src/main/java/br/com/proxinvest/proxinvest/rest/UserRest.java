package br.com.proxinvest.proxinvest.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // Como fazer?
    @GetMapping(value = "/user/{id}", produces = "application/json;charset=UTF-8")
    public List<UserDTO> getById(@PathVariable("id") Integer id) 
    {
        Optional<User> user = repo.findById(id);
        // List<User> user = repo.findAll();

        if (user != null) {
            return user.stream().map(e -> mapper.map(e, UserDTO.class)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @PostMapping(value = "/user/", produces = "application/json;charset=UTF-8")
    public UserDTO inserir(@RequestBody UserDTO user) 
    {
        // salva a Entidade convertida do DTO
        User u = mapper.map(user, User.class);
        repo.save(u);
        // busca o usuário inserido
        u = repo.findById(u.getId());
        // retorna o DTO equivalente à entidade
        return mapper.map(u, UserDTO.class);
    }

    // exemplo do trabalho de web
    // @PostMapping(value = "/produtos/", produces =
    // "application/json;charset=UTF-8")
    // public ProdutoDTO inserir(@RequestBody ProdutoDTO produto) {
    // // salva a Entidade convertida do DTO
    // Produto p = mapper.map(produto, Produto.class);
    // repo.save(p);
    // // busca o usuário inserido
    // Optional<Produto> produt = repo.findById(p.getId());
    // // retorna o DTO equivalente à entidade
    // return mapper.map(produt, ProdutoDTO.class);
    // }
}