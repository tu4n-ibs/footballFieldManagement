package org.example.myproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime bookingTime;
    private boolean status = true;
    private int totalPrice;
    @ManyToOne
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private FootballFields footballFields;
}
