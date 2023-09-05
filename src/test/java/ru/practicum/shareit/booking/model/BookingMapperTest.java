package ru.practicum.shareit.booking.model;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.OutputBookingDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {
    private final EasyRandom generator = new EasyRandom();

    @Test
    void testToBookingDto() {
        Booking booking = generator.nextObject(Booking.class);
        BookingDto bookingDto = BookingMapper.toBookingDto(booking);
        assertEquals(booking.getId(), bookingDto.getId());
        assertEquals(booking.getItem().getId(), bookingDto.getItem().getId());
        assertEquals(booking.getEnd(), bookingDto.getEnd());
        assertEquals(booking.getStart(), bookingDto.getStart());
        assertEquals(booking.getBooker().getId(), bookingDto.getBookerId());
    }

    @Test
    void testToOutputBookingDto() {
        Booking booking = generator.nextObject(Booking.class);
        OutputBookingDto bookingDto = BookingMapper.toOutputBookingDto(booking);
        assertEquals(booking.getId(), bookingDto.getId());
        assertEquals(booking.getItem().getId(), bookingDto.getItem().getId());
        assertEquals(booking.getEnd(), bookingDto.getEnd());
        assertEquals(booking.getStart(), bookingDto.getStart());
        assertEquals(booking.getBooker().getId(), bookingDto.getBooker().getId());
    }

    @Test
    void testToOutputsBookingDtoList() {
        Booking booking1 = generator.nextObject(Booking.class);
        Booking booking2 = generator.nextObject(Booking.class);
        List<OutputBookingDto> bookingDtoList = BookingMapper.toOutputsBookingDtoList(List.of(booking1, booking2));
        assertEquals(booking1.getId(), bookingDtoList.get(0).getId());
        assertEquals(booking1.getItem().getId(), bookingDtoList.get(0).getItem().getId());
        assertEquals(booking1.getEnd(), bookingDtoList.get(0).getEnd());
        assertEquals(booking1.getStart(), bookingDtoList.get(0).getStart());
        assertEquals(booking1.getBooker().getId(), bookingDtoList.get(0).getBooker().getId());
        assertEquals(booking2.getId(), bookingDtoList.get(1).getId());
        assertEquals(booking2.getItem().getId(), bookingDtoList.get(1).getItem().getId());
        assertEquals(booking2.getEnd(), bookingDtoList.get(1).getEnd());
        assertEquals(booking2.getStart(), bookingDtoList.get(1).getStart());
        assertEquals(booking2.getBooker().getId(), bookingDtoList.get(1).getBooker().getId());
    }
}