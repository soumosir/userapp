package com.soumosir.userapp.entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="USER_COLLECTION")
@Data
@AllArgsConstructor(staticName="build")
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(unique=true)
    private String username;
    private LocalDate dateOfBirth;
    private String address;
}
