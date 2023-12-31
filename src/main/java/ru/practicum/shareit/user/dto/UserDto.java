package ru.practicum.shareit.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private long id;
    @NotBlank
    @Size(max = 50)
    private String name;
    @NotBlank
    @Email(message = "incorect email")
    @Size(max = 100)
    private String email;
}