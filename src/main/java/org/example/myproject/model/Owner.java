package org.example.myproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String avatar;
    private boolean status;
    private boolean state;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
