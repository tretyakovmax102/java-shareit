package shareit.booking.model;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.OutputBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingMapperTest {
    private final EasyRandom generator = new EasyRandom();

    @Test
    void testToBookingDto() {
        Booking booking = generator.nextObject(Booking.class);
        BookingDto bookingDto = BookingMapper.toBookingDto(booking);
        assertEquals(bookingDto.getId(), booking.getId());
        assertEquals(bookingDto.getItem().getId(), booking.getItem().getId());
        assertEquals(bookingDto.getEnd(), booking.getEnd());
        assertEquals(bookingDto.getStart(), booking.getStart());
        assertEquals(bookingDto.getBookerId(), booking.getBooker().getId());
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
        assertEquals(bookingDtoList.get(0).getId(), booking1.getId());
        assertEquals(bookingDtoList.get(0).getItem().getId(), booking1.getItem().getId());
        assertEquals(bookingDtoList.get(0).getEnd(), booking1.getEnd());
        assertEquals(bookingDtoList.get(0).getStart(), booking1.getStart());
        assertEquals(bookingDtoList.get(0).getBooker().getId(), booking1.getBooker().getId());
        assertEquals(bookingDtoList.get(1).getId(), booking2.getId());
        assertEquals(bookingDtoList.get(1).getItem().getId(), booking2.getItem().getId());
        assertEquals(bookingDtoList.get(1).getEnd(), booking2.getEnd());
        assertEquals(bookingDtoList.get(1).getStart(), booking2.getStart());
        assertEquals(bookingDtoList.get(1).getBooker().getId(), booking2.getBooker().getId());
    }
}