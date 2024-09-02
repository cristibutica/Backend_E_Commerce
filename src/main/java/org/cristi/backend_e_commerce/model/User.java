package org.cristi.backend_e_commerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

}
