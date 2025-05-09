package br.com.proxinvest.proxinvest.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.proxinvest.proxinvest.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter //cria getters para todos os var
@Setter //cria setters para todos os var
public class WalletDTO {
    private int id;
    private String name;
    private String description;
    private Double walletValue;
    //fazer o hackzinho para puxar o objeto 
    @JsonIgnore
    private UserDTO user;
    // para de ser BURRO, meu 

}
