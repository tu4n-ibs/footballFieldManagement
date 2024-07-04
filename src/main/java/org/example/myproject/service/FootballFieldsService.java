package org.example.myproject.service;

import org.example.myproject.model.FootballFields;

import java.util.List;
import java.util.Optional;

public interface FootballFieldsService {
    List<FootballFields> findAll();
    Optional<FootballFields> findById(long id);
    FootballFields save(FootballFields footballFields);
    void deleteById(long id);
}
