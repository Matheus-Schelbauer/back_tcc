package br.com.proxinvest.proxinvest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "asset_wallet")
@Getter
@Setter
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="ticket_code")
    private String ticketCode;

    @Column(name="quantity")
    private Double quantity;

    @Column(name="unitary_value")
    private Double unitaryValue;

    @Column(name="total_value")
    private Double totalValue;

    //terá que fazer a conexão com as carteiras e o ativo original
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    private Wallet wallet;
  
    @Column(name="asset_original_id")
    private int assetOriginalId;


}
