package br.com.proxinvest.proxinvest.model;

import java.math.BigDecimal;
import java.util.List;

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
@Table(name = "asset_original")
@Getter
@Setter
public class AssetOriginal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name="unitary_value")
    private BigDecimal unitaryValue;

    @Column(name="ticket_code")
    private String ticketCode;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "assetOriginal")
    @JsonIgnore
    private List<Asset> assets;

}
