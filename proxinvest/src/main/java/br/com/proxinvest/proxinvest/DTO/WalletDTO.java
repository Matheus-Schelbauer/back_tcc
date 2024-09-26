package br.com.proxinvest.proxinvest.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter //cria getters para todos os var
@Setter //cria setters para todos os var
public class WalletDTO {
    private int id;
    private String name;
    private String description;
    private Double walletValue;
    //fazer o hackzinho hehe, para puxar o objeto user hehe

}
