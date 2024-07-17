package org.example.myproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.myproject.dto.FootballFieldsDTO;
import org.example.myproject.dto.FootballFieldsSpec;
import org.example.myproject.model.FootballFields;
import org.example.myproject.model.TimeFields;
import org.example.myproject.model.User;
import org.example.myproject.repository.FootballFieldsRepository;
import org.example.myproject.service.FootballFieldsService;
import org.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FootballFieldsServiceImpl implements FootballFieldsService {

    @Autowired
    private FootballFieldsRepository fieldsRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<FootballFields> findAll(long id) {
        return fieldsRepository.findAllByUserId(id);
    }

    @Override
    public Optional<FootballFields> findById(long id) {
        return fieldsRepository.findById(id);
    }

    @Override
    public FootballFields save(FootballFields footballFields) {
        footballFields.getUser().setId(footballFields.getUser().getId());
        return fieldsRepository.save(footballFields);
    }

    @Override
    public void deleteById(long id) {
        fieldsRepository.deleteById(id);
    }

    @Override
    public List<FootballFields> findAll() {
        return fieldsRepository.findAll();
    }

    @Override
    public FootballFields update(long id, boolean status) {
        FootballFields footballFields = fieldsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Field not found"));
        footballFields.setStatus(status);
        return fieldsRepository.save(footballFields);
    }

    @Override
    public List<TimeFields> getTimeFieldsByFieldId(Long fieldId) {
        FootballFields footballField = fieldsRepository.findById(fieldId)
                .orElseThrow(RuntimeException::new);
        return footballField.getTimeFields();
    }

    public List<FootballFields> search(FootballFieldsDTO footballFieldsDTO) {
        return fieldsRepository.findAll(
                new FootballFieldsSpec(footballFieldsDTO)
        );
    }
}
