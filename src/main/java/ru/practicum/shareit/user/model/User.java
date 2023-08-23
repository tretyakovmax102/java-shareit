package ru.practicum.shareit.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * TODO Sprint add-controllers.
 */

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @NotBlank
    @Size(max = 50)
    @Column(name = "username", nullable = false)
    private String name;
    @NotBlank
    @Email(message = "incorrect email")
    @Size(max = 256)
    @Column(unique = true, nullable = false)
    private String email;
}
