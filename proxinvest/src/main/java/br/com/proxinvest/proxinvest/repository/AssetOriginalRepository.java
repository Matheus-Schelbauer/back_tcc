package br.com.proxinvest.proxinvest.repository;

import br.com.proxinvest.proxinvest.model.AssetOriginal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface AssetOriginalRepository extends JpaRepository<AssetOriginal, Integer> {
    // Aqui tá a mágica: busca por ticketCode
    Optional<AssetOriginal> findByTicketCode(String ticketCode);
}