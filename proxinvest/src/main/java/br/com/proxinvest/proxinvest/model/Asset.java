package br.com.proxinvest.proxinvest.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
// Fazer a conexão com o Asset Original

@Entity
@Table(name = "asset_wallet")
@Getter
@Setter
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ticket_code")
    private String ticketCode;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unitary_value")
    private BigDecimal unitaryValue;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    // terá que fazer a conexão com as carteiras e o ativo original
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    private Wallet wallet;

    // Fazer a conexão com o Asset Original
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_code", referencedColumnName = "ticket_code", insertable = false, updatable = false)
    private AssetOriginal assetOriginal;
}
