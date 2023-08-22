package ru.practicum.shareit.user.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
@AllArgsConstructor
public class User {
    private long id;
    @NotBlank
    @Size(max = 50)
    private String name;
    @NotBlank
    @Email(message = "incorrect email")
    @Size(max = 256)
    private String email;
}
