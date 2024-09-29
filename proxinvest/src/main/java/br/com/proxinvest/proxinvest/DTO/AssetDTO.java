package br.com.proxinvest.proxinvest.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter //cria getters para todos os var
@Setter //cria setters para todos os var
public class AssetDTO {
    private int id;
    private String ticketCode;
    private Double quantity;
    private Double unitaryValue;
    private Double totalValue;
    private int walletId;
    private int assetOriginalId;

    //fazer o hackzinho para puxar o objeto user talvez alterar para que similarmente fique a chamada da Wallet com base no user
    @JsonIgnore
    private WalletDTO wallet;

}
