package org.example.myproject.controller;

import org.example.myproject.model.FootballFields;
import org.example.myproject.service.FootballFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private FootballFieldsService fieldsService;

    @GetMapping("/getAllFields")
    public ResponseEntity getAllFields() {
        List<FootballFields> footballFields = fieldsService.findAll();
        return new ResponseEntity<>(footballFields, HttpStatus.OK);
    }

    @PostMapping("/createFields")
    public ResponseEntity createFields(@RequestBody FootballFields footballFields) {
        FootballFields footballFieldsSaved = fieldsService.save(footballFields);
        return new ResponseEntity<>(footballFieldsSaved, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delateFields")
    public ResponseEntity deleteFields(@PathVariable long id) {
        fieldsService.deleteById(id);
        return new ResponseEntity("deleted success",HttpStatus.OK);
    }
}
