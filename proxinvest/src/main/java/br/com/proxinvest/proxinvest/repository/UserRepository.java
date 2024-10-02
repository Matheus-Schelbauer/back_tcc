package br.com.proxinvest.proxinvest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.proxinvest.proxinvest.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    public User findById(int id);
}
