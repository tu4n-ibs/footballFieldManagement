package org.example.myproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.myproject.model.Booking;
import org.example.myproject.model.FootballFields;
import org.example.myproject.repository.BookingRepository;
import org.example.myproject.service.BookingService;
import org.example.myproject.service.FootballFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private FootballFieldsService fieldsService;

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findBookingById(long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking bookingRequest) {
        FootballFields footballFields = fieldsService.findById(bookingRequest.getFootballFields().getId())
                .orElseThrow(() -> new EntityNotFoundException("Field not found"));

        Booking booking = new Booking();
        booking.setBookingTime(bookingRequest.getBookingTime());
        booking.setTotalPrice(bookingRequest.getTotalPrice());
        booking.setUser(bookingRequest.getUser());
        booking.setFootballFields(footballFields);

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> findAllBookingByUserId(Long userId) {
        return bookingRepository.findAllBookingByUserId(userId);
    }

    @Override
    public List<Booking> findAllBookingByFootballFieldsId(Long footballFieldsId) {
        return bookingRepository.findAllBookingByFootballFieldsId(footballFieldsId);
    }
}
