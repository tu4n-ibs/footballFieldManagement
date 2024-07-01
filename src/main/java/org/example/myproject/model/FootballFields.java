package org.example.myproject.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FootballFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String quantity;
    private int price;
    private String location;
    private boolean status;
    private String picture;
    @ManyToOne
    private Owner owner;
}
