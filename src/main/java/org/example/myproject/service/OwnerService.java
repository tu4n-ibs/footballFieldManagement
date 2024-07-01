package org.example.myproject.service;

import org.example.myproject.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    List<Owner> findAll();
    Optional<Owner> findById(long id);
    Owner save(Owner owner);
    void delete(long id);
    Owner acceptOwner(long id);
}
