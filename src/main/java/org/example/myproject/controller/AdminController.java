package org.example.myproject.controller;

import org.example.myproject.model.Owner;
import org.example.myproject.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Owner>> getAllOwner() {
        List<Owner> owners = ownerService.findAll();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @PostMapping("/createOwner")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner newOwner = ownerService.save(owner);
        return new ResponseEntity<>(newOwner, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/deleteOwner")
    public ResponseEntity<Owner> deleteOwner(@PathVariable int id) {
        ownerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/updateOwner")
    public ResponseEntity<Owner> updateOwner(@PathVariable long id, @RequestBody Owner owner) {
        owner.setId(id);
        Owner newOwner = ownerService.save(owner);
        return new ResponseEntity<>(newOwner, HttpStatus.OK);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Owner> acceptOwner(@PathVariable long id) {
        Owner owner = ownerService.acceptOwner(id);
        if (owner != null) {
            return ResponseEntity.ok(owner);
        }
        return ResponseEntity.notFound().build();
    }
}
