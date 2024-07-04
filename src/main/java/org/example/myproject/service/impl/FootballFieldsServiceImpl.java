package org.example.myproject.service.impl;

import org.example.myproject.model.FootballFields;
import org.example.myproject.model.User;
import org.example.myproject.repository.FootballFieldsRepository;
import org.example.myproject.service.FootballFieldsService;
import org.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<FootballFields> findAll() {
        return fieldsRepository.findAll();
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
}
