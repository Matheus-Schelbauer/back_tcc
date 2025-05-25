package br.com.proxinvest.proxinvest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.model.Wallet;

public interface AssetRepository extends JpaRepository<Asset,Integer>{
    public List<Asset> findByWallet_Id(int walletId);

    public Asset findById(int id);

    //
}