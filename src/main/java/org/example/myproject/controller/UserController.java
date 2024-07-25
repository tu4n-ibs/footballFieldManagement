package org.example.myproject.controller;

import org.example.myproject.dto.FootballFieldsDTO;
import org.example.myproject.model.*;
import org.example.myproject.service.BookingService;
import org.example.myproject.service.FootballFieldsService;
import org.example.myproject.service.RoleService;
import org.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private FootballFieldsService fieldsService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}/getBooking")
    public ResponseEntity getBooking(@PathVariable long id) {
        List<Booking> bookingList = bookingService.findAllBookingByUserId(id);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking bookingSaved = bookingService.save(booking);
        return new ResponseEntity<>(bookingSaved, HttpStatus.CREATED);
    }

    @GetMapping("/getAllFields")
    public ResponseEntity getAllFields() {
        List<FootballFields> footballFields = fieldsService.findAll();
        return new ResponseEntity<>(footballFields, HttpStatus.OK);
    }

    @GetMapping("/{id}/getField")
    public ResponseEntity getFields(@PathVariable long id) {
        Optional<FootballFields> footballFields = fieldsService.findById(id);
        return new ResponseEntity<>(footballFields.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}/time")
    public List<TimeFields> getTime(@PathVariable long id) {
        return fieldsService.getTimeFieldsByFieldId(id);
    }

    @GetMapping("/getAllOwner")
    public ResponseEntity getAllOwner() {
        Role role = roleService.findByName("Role_manage");
        List<User> ownerList = userService.findAllByRoles(role);
        return new ResponseEntity<>(ownerList, HttpStatus.OK);
    }

    @GetMapping("/{id}/getAllFields")
    public ResponseEntity getAllFields(@PathVariable long id) {
        List<FootballFields> footballFields = fieldsService.findAll(id);
        return new ResponseEntity<>(footballFields, HttpStatus.OK);
    }

    @GetMapping("/{id}/getOwner")
    public ResponseEntity getAllOwner(@PathVariable long id) {
        Optional<User> ownerList = userService.findById(id);
        return new ResponseEntity<>(ownerList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "location", required = false) String location,
                                 @RequestParam(value = "typeFields", required = false) String typeFields) {
        List<FootballFields> footballFields = fieldsService.search(
                new FootballFieldsDTO(name, location, typeFields)
        );
        return new ResponseEntity<>(footballFields, HttpStatus.OK);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity getUser(@PathVariable long id) {
        Optional<User> user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/updateUser")
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        userService.save(user);
        return new ResponseEntity<>("update successful",HttpStatus.OK);
    }
}
