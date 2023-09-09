package shareit.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.dto.OutputBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @MockBean
    BookingService bookingService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void testGetBookingDto() throws Exception {
        OutputBookingDto outputBookingDto = new OutputBookingDto(1L, LocalDateTime.now(),
                LocalDateTime.of(2022, 2, 2,0, 0,0).plusDays(1),
                new ItemDto(), new UserDto(1, "name", "desk"), BookingStatus.WAITING);
        when(bookingService.getBookingDto(anyLong(), anyLong()))
                .thenReturn(outputBookingDto);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id",1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1L), Long.class))
                .andExpect(jsonPath("$.status",is(outputBookingDto.getStatus().toString()), String.class));
    }

    @Test
    void testCreate() {
        InputBookingDto bookingIn = InputBookingDto.builder()
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .itemId(1L)
                .build();

        when(bookingService.create(Mockito.any(InputBookingDto.class), anyLong()))
                .thenAnswer(u -> {
                    InputBookingDto itemMock = u.getArgument(1, InputBookingDto.class);
                    Booking booking = new Booking(new Item(),new User(),itemMock.getStart(),
                            itemMock.getEnd(), BookingStatus.WAITING);
                    booking.setId(1L);
                    return booking;
                });
    }
}