package br.com.proxinvest.proxinvest.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.proxinvest.proxinvest.model.Asset;
import br.com.proxinvest.proxinvest.model.Wallet;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
    // public List<Asset> findByWallet_Id(int walletId);
    @Query("SELECT a FROM Asset a JOIN FETCH a.assetOriginal")
    List<Asset> findAllWithOriginal();

    @Query("SELECT a FROM Asset a JOIN FETCH a.assetOriginal WHERE a.wallet.id = :walletId")
    List<Asset> findByWalletIdWithAssetOriginal(@Param("walletId") int walletId);

    @Query("SELECT COALESCE(SUM(a.totalValue), 0) FROM Asset a WHERE a.wallet.id = :walletId")
    BigDecimal calcularValorTotalDaWallet(@Param("walletId") Integer walletId);

    public Asset findById(int id);

    //
}