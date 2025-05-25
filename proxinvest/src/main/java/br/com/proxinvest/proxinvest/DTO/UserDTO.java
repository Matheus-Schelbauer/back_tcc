package br.com.proxinvest.proxinvest.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.proxinvest.proxinvest.model.Wallet;
import lombok.Getter;
import lombok.Setter;

@Getter //cria getters para todos os var
@Setter //cria setters para todos os var
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    
    @JsonIgnore
    private List<WalletDTO> wallets;
}
