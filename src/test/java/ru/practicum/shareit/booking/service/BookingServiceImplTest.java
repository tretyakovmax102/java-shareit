package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private BookingRepository bookingRepository;
    private final LocalDateTime date = LocalDateTime.of(2023, 1, 10, 1,1);


    @Test
    void testCreateGet() {
        InputBookingDto booking = InputBookingDto.builder()
                .start(date.plusDays(1))
                .end(date.plusDays(2))
                .itemId(1L)
                .build();

        User user = new User(1L,"name1","user1@user.ru");
        User user2 = new User(2L,"name2","user2@user.ru");
        Item item = new Item(1L,"item","description",true);
        item.setOwner(user);
        when(userService.getUser(anyLong()))
                .thenReturn(null);
        when(itemService.getItemById(anyLong()))
                .thenReturn(null);
        when(userService.getUser(user.getId()))
                .thenReturn(user);
        when(userService.getUser(user2.getId()))
                .thenReturn(user2);
        when(itemService.getItemById(booking.getItemId()))
                .thenReturn(item);
        assertThrows(NotFoundException.class, () -> bookingService.create(booking, 1L));

        bookingService.create(booking, 2L);

        verify(bookingRepository,times(1)).save(any(Booking.class));

        Booking booking1 = new Booking(item, user, booking.getStart(), booking.getEnd(), BookingStatus.APPROVED);
        when(bookingRepository.findById(booking.getItemId()))
                .thenReturn(Optional.of(booking1));
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(booking.getItemId(), 2L));
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(booking.getItemId(), 0L));
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(booking.getItemId(), -10L));
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(0L, 0L));
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(-10L, 1L));
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(10L, 10L));
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(10L, 1L));

        bookingService.getBooking(booking.getItemId(), 1L);
    }

    @Test
    void testGetBookingBooker() {
        when(userService.getUser(anyLong()))
                .thenReturn(null);
        when(bookingRepository.findAllByBookerIdAndStatus(anyLong(), any(BookingStatus.class), any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByBookerIdOrderByStartDesc(anyLong(), any(Pageable.class)))
                .thenReturn(List.of());


        bookingService.getBookingBooker(State.WAITING, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByBookerIdAndStatus(anyLong(), any(BookingStatus.class), any(Pageable.class));

        bookingService.getBookingBooker(State.REJECTED, 1L, 1L, 1L);

        verify(bookingRepository,times(2))
                .findAllByBookerIdAndStatus(anyLong(), any(BookingStatus.class), any(Pageable.class));

        bookingService.getBookingBooker(State.PAST, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any(LocalDateTime.class), any(Pageable.class));

        bookingService.getBookingBooker(State.FUTURE, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class), any(Pageable.class));

        bookingService.getBookingBooker(State.CURRENT, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class));

        bookingService.getBookingBooker(State.ALL, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByBookerIdOrderByStartDesc(anyLong(), any(Pageable.class));

        assertEquals(List.of(), bookingService.getBookingBooker(State.ALL,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingBooker(State.CURRENT,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingBooker(State.REJECTED,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingBooker(State.FUTURE,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingBooker(State.PAST,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingBooker(State.WAITING,anyLong(),null,null));
    }

    @Test
    void testGetBookingOwner() {
        when(userService.getUser(anyLong()))
                .thenReturn(null);
        when(bookingRepository.findAllByOwnerIdAndStatus(anyLong(), any(BookingStatus.class), any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByOwnerIdAndEndBefore(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByOwnerIdAndStartAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(List.of());
        when(bookingRepository.findAllByOwnerId(anyLong(), any(Pageable.class)))
                .thenReturn(List.of());


        bookingService.getBookingOwner(State.WAITING, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByOwnerIdAndStatus(anyLong(), any(BookingStatus.class), any(Pageable.class));

        bookingService.getBookingOwner(State.REJECTED, 1L, 1L, 1L);

        verify(bookingRepository,times(2))
                .findAllByOwnerIdAndStatus(anyLong(), any(BookingStatus.class), any(Pageable.class));

        bookingService.getBookingOwner(State.PAST, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByOwnerIdAndEndBefore(anyLong(), any(LocalDateTime.class), any(Pageable.class));

        bookingService.getBookingOwner(State.FUTURE, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByOwnerIdAndStartAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class));

        bookingService.getBookingOwner(State.CURRENT, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class));

        bookingService.getBookingOwner(State.ALL, 1L, 1L, 1L);

        verify(bookingRepository,times(1))
                .findAllByOwnerId(anyLong(), any(Pageable.class));

        assertEquals(List.of(), bookingService.getBookingOwner(State.ALL,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingOwner(State.CURRENT,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingOwner(State.REJECTED,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingOwner(State.FUTURE,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingOwner(State.PAST,anyLong(),null,null));
        assertEquals(List.of(),bookingService.getBookingOwner(State.WAITING,anyLong(),null,null));

    }
}