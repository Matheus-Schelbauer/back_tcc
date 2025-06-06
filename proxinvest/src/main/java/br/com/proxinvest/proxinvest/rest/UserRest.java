package br.com.proxinvest.proxinvest.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.proxinvest.proxinvest.DTO.AuthRequestDTO;
import br.com.proxinvest.proxinvest.DTO.LoginDTO;
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
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Integer id) {
        Optional<User> user = repo.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(mapper.map(user.get(), UserDTO.class));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/user/", produces = "application/json;charset=UTF-8")
    public UserDTO inserir(@RequestBody UserDTO user) {
        // salva a Entidade convertida do DTO
        User u = mapper.map(user, User.class);
        repo.save(u);
        // busca o usuário inserido
        u = repo.findById(u.getId());
        // retorna o DTO equivalente à entidade
        return mapper.map(u, UserDTO.class);
    }

    @PostMapping("/signup") // talvez mudar a rota depois
    public ResponseEntity<?> register(@RequestBody UserDTO userDto) {
        User user = mapper.map(userDto, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // criptografando a senha
        repo.save(user);
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    // @PostMapping("/signin") // talvez mudar a rota depois
    // public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
    // Optional<User> userOpt = repo.findByEmail(request.getEmail());

    // if (userOpt.isPresent()) {
    // User user = userOpt.get();
    // if (new BCryptPasswordEncoder().matches(request.getPassword(),
    // user.getPassword())) {
    // return ResponseEntity.ok("Login realizado com sucesso!");
    // }
    // }
    // return ResponseEntity.status(401).body("Credenciais inválidas");
    // }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<User> userOpt = repo.findByEmail(loginDTO.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getPassword().equals(loginDTO.getPassword())) {
                // Cria um DTO de resposta só com os dados que você quer expor
                UserDTO response = new UserDTO();
                response.setId(user.getId());
                response.setName(user.getName());
                response.setEmail(user.getEmail());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Senha incorreta");
            }
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }

}