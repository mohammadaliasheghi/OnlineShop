package com.google.OnlineShop.app.repository;

import com.google.OnlineShop.app.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users getByUsername(String username);

}
