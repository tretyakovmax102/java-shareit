package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.dto.OutputBookingDto;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private static final String LINE = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public OutputBookingDto getBookingDto(@RequestHeader(LINE) Long userId, @PathVariable Long bookingId) {
        return bookingService.getBookingDto(bookingId, userId);
    }

    @GetMapping
    public List<OutputBookingDto> getBookingUser(@RequestHeader(LINE) Long userId,
                                                 @RequestParam(defaultValue = "ALL") String state,
                                                 @RequestParam(value = "from", required = false) @PositiveOrZero Long from,
                                                 @RequestParam(value = "size", required = false) @PositiveOrZero Long size) {
        return bookingService.getBookingBooker(State.getState(state), userId, from, size);
    }

    @GetMapping("/owner")
    public List<OutputBookingDto> getBookingOwner(@RequestHeader(LINE) Long userId,
                                                  @RequestParam(defaultValue = "ALL") String state,
                                                  @RequestParam(value = "from", required = false) @PositiveOrZero Long from,
                                                  @RequestParam(value = "size", required = false) @PositiveOrZero Long size) {
        return bookingService.getBookingOwner(State.getState(state), userId, from, size);
    }

    @PostMapping
    public OutputBookingDto create(@RequestHeader(LINE) long userId,
                                   @Valid @RequestBody InputBookingDto booking) {
        return bookingService.create(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public OutputBookingDto update(@RequestHeader(LINE) Long userId,
                                   @PathVariable Long bookingId,
                                   @RequestParam boolean approved) {
        return bookingService.updateBooking(bookingId, userId, approved);
    }


}
