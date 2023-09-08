package ru.practicum.shareit.booking.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputBookingDto {
    private Long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
}
