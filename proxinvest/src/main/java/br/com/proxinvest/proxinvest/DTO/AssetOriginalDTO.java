package br.com.proxinvest.proxinvest.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter //cria getters para todos os var
@Setter //cria setters para todos os var
public class AssetOriginalDTO {
    private int id;
    private Double unitaryValue;
    private String ticketCode;
    private String name;

}
