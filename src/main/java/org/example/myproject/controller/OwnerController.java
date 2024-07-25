package org.example.myproject.controller;

import org.example.myproject.model.Booking;
import org.example.myproject.model.FootballFields;
import org.example.myproject.model.TimeFields;
import org.example.myproject.service.BookingService;
import org.example.myproject.service.FootballFieldsService;
import org.example.myproject.service.TimeFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private FootballFieldsService fieldsService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TimeFieldsService timeFieldsService;

    @GetMapping("/{id}/getAllFields")
    public ResponseEntity getAllFields(@PathVariable long id) {
        List<FootballFields> footballFields = fieldsService.findAll(id);
        return new ResponseEntity<>(footballFields, HttpStatus.OK);
    }

    @PostMapping("/createFields")
    public ResponseEntity createFields(@RequestBody FootballFields footballFields) {
        FootballFields footballFieldsSaved = fieldsService.save(footballFields);
        return new ResponseEntity<>(footballFieldsSaved, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/deleteFields")
    public ResponseEntity deleteFields(@PathVariable long id) {
        fieldsService.deleteById(id);
        return new ResponseEntity("deleted success", HttpStatus.OK);
    }

    @PutMapping("/{id}/updateFields")
    public ResponseEntity updateFields(@RequestBody FootballFields footballFields, @PathVariable long id, boolean status) {
        footballFields.setId(id);
        footballFields.setStatus(status);
        fieldsService.save(footballFields);
        return new ResponseEntity("updated success", HttpStatus.OK);
    }

    @GetMapping("/{id}/findFields")
    public ResponseEntity findFields(@PathVariable long id) {
        Optional<FootballFields> fields = fieldsService.findById(id);
        return new ResponseEntity<>(fields, HttpStatus.OK);
    }

    @GetMapping("/getAllBooking")
    public ResponseEntity getAllBooking() {
        List<Booking> bookingList = bookingService.findAllBookings();
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @GetMapping("/{id}/getBooking")
    public ResponseEntity getBooking(@PathVariable long id) {
        List<Booking> bookingList = bookingService.findAllBookingByFootballFieldsId(id);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<FootballFields> updateStatus(@PathVariable long id, @RequestParam boolean status) {
        FootballFields updatedField = fieldsService.update(id, status);
        return new ResponseEntity<>(updatedField, HttpStatus.OK);
    }

    @GetMapping("/time")
    public ResponseEntity getTime() {
        List<TimeFields> timeFields = timeFieldsService.findAll();
        return new ResponseEntity<>(timeFields, HttpStatus.OK);
    }


}
