package org.example.myproject.service.impl;

import org.example.myproject.model.TimeFields;
import org.example.myproject.repository.TimeFieldsRepository;
import org.example.myproject.service.TimeFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeFieldsServiceImpl implements TimeFieldsService {

    @Autowired
    private TimeFieldsRepository timeFieldsRepository;

    @Override
    public List<TimeFields> findAll() {
        return timeFieldsRepository.findAll();
    }
}
