package br.com.proxinvest.proxinvest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.proxinvest.proxinvest.model.User;
import br.com.proxinvest.proxinvest.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer>{
    public List<Wallet> findByUser_id(int id);

    public Wallet findById(int id);

    //Não é necessário declarar o delete, pis o JPA já me dá isso
}
