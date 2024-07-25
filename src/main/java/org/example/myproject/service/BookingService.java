package org.example.myproject.service;

import org.example.myproject.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> findAllBookings();
    Optional<Booking> findBookingById(long id);
    Booking save(Booking booking);
    void deleteBooking(long id);
    List<Booking> findAllBookingByUserId(Long userId);
    List<Booking> findAllBookingByFootballFieldsId(Long footballFieldsId);
}
