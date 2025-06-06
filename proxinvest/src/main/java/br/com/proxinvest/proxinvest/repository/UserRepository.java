package br.com.proxinvest.proxinvest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.proxinvest.proxinvest.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    public User findById(int id);

    Optional<User> findByEmail(String email);

}
