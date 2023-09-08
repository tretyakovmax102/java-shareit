package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.dto.OutputBookingDto;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookingServiceImplIntegrationTest {

    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Test
    @DirtiesContext
    void testCreateUpdateGet() {
        UserDto user1 = userService.create(User.builder()
                .name("name1")
                .email("name1@mail.com")
                .build());

        UserDto user2 = userService.create(User.builder()
                .name("name2")
                .email("name2@mail.com")
                .build());

        ItemDtoRequest item1 = itemService.add(user1.getId(),
                ItemDtoRequest.builder()
                        .name("item")
                        .description("description")
                        .available(true)
                        .build());

        OutputBookingDto booking = bookingService.create(InputBookingDto.builder()
                .start(LocalDateTime.of(2023,10,20, 1, 1).plusDays(1))
                .end(LocalDateTime.of(2023,10,20, 1, 1).plusDays(2))
                .itemId(item1.getId())
                .build(), user2.getId());

        assertNotNull(booking);
        assertEquals(booking.getStatus(), BookingStatus.WAITING);
        OutputBookingDto bookingChangerStatus = bookingService.updateBooking(booking.getId(), user1.getId(),true);
        assertEquals(bookingChangerStatus.getStatus(), BookingStatus.APPROVED);
    }
}