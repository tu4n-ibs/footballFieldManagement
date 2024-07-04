package org.example.myproject.controller;

import org.example.myproject.model.Role;
import org.example.myproject.model.User;
import org.example.myproject.service.RoleService;
import org.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllOwner")
    public ResponseEntity getAllOwner() {
        Role role = roleService.findByName("Role_manage");
        List<User> ownerList = userService.findAllByRoles(role);
        return new ResponseEntity<>(ownerList, HttpStatus.OK);
    }
    @PutMapping("/{id}/updateOwner")
    public ResponseEntity updateOwner(@RequestBody User user, @PathVariable long id) {
        user.setId(id);
        userService.save(user);
        return new ResponseEntity<>("update success",HttpStatus.OK);
    }




}
