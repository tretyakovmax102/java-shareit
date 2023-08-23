package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private static final String line = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public OutputBookingDto getBookingDto(@RequestHeader(line) Long userId, @PathVariable Long bookingId) {
        return bookingService.getBookingDto(bookingId, userId);
    }

    @GetMapping
    public List<OutputBookingDto> getBookingUser(@RequestHeader(line) Long userId,
                                                 @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getBookingBooker(State.getState(state), userId);
    }

    @GetMapping("/owner")
    public List<OutputBookingDto> getBookingOwner(@RequestHeader(line) Long userId,
                                                  @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getBookingOwner(State.getState(state), userId);
    }

    @PostMapping
    public OutputBookingDto create(@RequestHeader(line) long userId,
                                   @Valid @RequestBody InputBookingDto booking) {
        return bookingService.create(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public OutputBookingDto update(@RequestHeader(line) Long userId,
                                   @PathVariable Long bookingId,
                                   @RequestParam boolean approved) {
        return bookingService.updateBooking(bookingId, userId, approved);
    }


}
