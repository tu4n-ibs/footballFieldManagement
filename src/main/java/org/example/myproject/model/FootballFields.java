package org.example.myproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
public class FootballFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String location;
    private boolean status;
    private String picture;
    private String typeFields;
    private String description;
    private LocalTime openTime;
    private LocalTime closeTime;

    @ManyToOne
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<TimeFields> timeFields;

}
