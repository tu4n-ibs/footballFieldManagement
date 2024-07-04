package org.example.myproject.service;

import org.example.myproject.model.Role;
import org.example.myproject.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Iterable<User> findAll();
    User save(User user);
    Optional<User> findById(Long id);
    User findByUsername(String username);
    List<User> findAllByRoles(Role role);
}
