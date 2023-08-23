package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InputBookingDto {
    @NotNull
    private Long itemId;
    @NotNull(message = "start = null")
    @FutureOrPresent(message = "start mustnt be future")
    private LocalDateTime start;
    @NotNull(message = "end = null")
    @Future(message = "end mustnt be past")
    private LocalDateTime end;
}