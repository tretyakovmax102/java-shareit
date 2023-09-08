package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.valid.ValidateDate;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ValidateDate
public class BookingDto {

    private Long id;
    @FutureOrPresent(message = "start exception")
    private LocalDateTime start;
    @FutureOrPresent(message = "end mustn't be in past")
    private LocalDateTime end;
    private Long itemId;
}
