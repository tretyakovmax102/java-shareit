package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.BookingDto;
<<<<<<<< HEAD:gateway/src/main/java/ru/practicum/shareit/item/dto/ItemDtoRequest.java

========
import ru.practicum.shareit.user.model.User;
>>>>>>>> main:src/main/java/ru/practicum/shareit/item/dto/ItemDto.java

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

<<<<<<<< HEAD:gateway/src/main/java/ru/practicum/shareit/item/dto/ItemDtoRequest.java
========
@Data
@AllArgsConstructor
@NoArgsConstructor
>>>>>>>> main:src/main/java/ru/practicum/shareit/item/dto/ItemDto.java
@Builder
@AllArgsConstructor
@Data
public class ItemDtoRequest {
    private long id;
    @NotBlank
    @Size(max = 50)
    private String name;
    @Size(max = 200)
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
<<<<<<<< HEAD:gateway/src/main/java/ru/practicum/shareit/item/dto/ItemDtoRequest.java
    private Long requestId;
========
    private User owner;
>>>>>>>> main:src/main/java/ru/practicum/shareit/item/dto/ItemDto.java
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments;
}