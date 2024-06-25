package org.example.myproject.repository;

import org.example.myproject.model.JwtToken;
import org.example.myproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    JwtToken findByTokenEquals(String token);
    JwtToken findByUser(User user);
}
