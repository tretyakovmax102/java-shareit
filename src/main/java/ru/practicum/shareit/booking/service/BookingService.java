package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.dto.OutputBookingDto;
import ru.practicum.shareit.booking.model.Booking;

import ru.practicum.shareit.booking.model.State;

import java.util.List;

public interface BookingService {

    OutputBookingDto create(InputBookingDto bookingDto, Long userId);

    OutputBookingDto updateBooking(Long bookingId, Long userId, Boolean approve);

    Booking getBooking(Long bookingId, Long userId);

    OutputBookingDto getBookingDto(Long bookingId, Long userId);

    List<OutputBookingDto> getBookingBooker(State state, Long bookerId, Long from, Long size);

    List<OutputBookingDto> getBookingOwner(State state, Long ownerId, Long from, Long size);

}