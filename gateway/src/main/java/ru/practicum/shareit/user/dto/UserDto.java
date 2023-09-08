package ru.practicum.shareit.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    @Size(max = 50)
    @NotBlank
    private String name;
    @Size(max = 100)
    @Email(message = "incorrect email")
    @NotBlank
    private String email;
}