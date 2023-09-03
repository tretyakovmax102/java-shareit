package ru.practicum.shareit.booking.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InputBookingDto {
    @NotNull
    private Long itemId;
    @NotNull(message = "start = null")
    @FutureOrPresent(message = "start mustn't be future")
    private LocalDateTime start;
    @NotNull(message = "end = null")
    @Future(message = "end mustn't be past")
    private LocalDateTime end;
}