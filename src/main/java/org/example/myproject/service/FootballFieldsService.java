package org.example.myproject.service;

import org.example.myproject.dto.FootballFieldsDTO;
import org.example.myproject.model.FootballFields;
import org.example.myproject.model.TimeFields;

import java.util.List;
import java.util.Optional;

public interface FootballFieldsService {
    List<FootballFields> findAll(long id);
    Optional<FootballFields> findById(long id);
    FootballFields save(FootballFields footballFields);
    void deleteById(long id);
    List<FootballFields> findAll();
    FootballFields update(long id, boolean status);
    List<TimeFields> getTimeFieldsByFieldId(Long fieldId);
    List<FootballFields> search(FootballFieldsDTO footballFieldsDTO);
}
