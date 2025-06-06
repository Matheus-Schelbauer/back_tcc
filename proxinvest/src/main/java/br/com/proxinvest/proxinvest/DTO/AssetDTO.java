package br.com.proxinvest.proxinvest.DTO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter // cria getters para todos os var
@Setter // cria setters para todos os var
public class AssetDTO {
    private int id;
    private String ticketCode;
    private Double quantity;
    private BigDecimal unitaryValue;
    private BigDecimal totalValue;
    private int walletId;
    // private int assetOriginalId;

    private AssetOriginalDTO assetOriginal;

}
