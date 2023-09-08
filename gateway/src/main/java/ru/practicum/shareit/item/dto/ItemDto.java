package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.booking.dto.BookingDto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

<<<<<<<< HEAD:gateway/src/main/java/ru/practicum/shareit/item/dto/ItemDto.java
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDto {
========
@Builder
@AllArgsConstructor
@Data
public class ItemDtoRequest {
>>>>>>>> origin/add-docker:gateway/src/main/java/ru/practicum/shareit/item/dto/ItemDtoRequest.java
    private long id;
    @NotBlank
    @Size(max = 50)
    private String name;
    @Size(max = 200)
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private Long requestId;
<<<<<<<< HEAD:gateway/src/main/java/ru/practicum/shareit/item/dto/ItemDto.java
}
========
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments;
}
>>>>>>>> origin/add-docker:gateway/src/main/java/ru/practicum/shareit/item/dto/ItemDtoRequest.java
