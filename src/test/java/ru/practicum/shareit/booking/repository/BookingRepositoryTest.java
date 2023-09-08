package ru.practicum.shareit.booking.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    BookingRepository bookingRepository;

    @Test
    @DirtiesContext
    void testFindAllByBookerIdOrderByStartDesc() {
        User user = new User(1L,"user1","user1@user.ru");
        userRepository.save(user);

        User user2 = new User(2L,"user2","user2@user.ru");
        userRepository.save(user2);

        Item item1 = new Item(1L,"item1","description",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        Item item2 = new Item(2L,"item2","description",true);
        item2.setOwner(userRepository.findById(2L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item2);

        LocalDateTime date = LocalDateTime.now();

        Booking booking1 = new Booking(item1,user2, date.minusDays(2),date.minusDays(1), BookingStatus.APPROVED);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking(item2,user2, date.plusDays(1),date.plusDays(1), BookingStatus.REJECTED);
        bookingRepository.save(booking2);

        List<Booking> booking = bookingRepository.findAllByBookerIdOrderByStartDesc(2L, Pageable.unpaged());
        assertEquals(booking.size(),2);
        assertEquals(booking.get(0).getId(),2L);
        assertEquals(booking.get(0).getItem().getId(),2L);
        assertEquals(booking.get(1).getId(),1L);
        assertEquals(booking.get(1).getItem().getId(),1L);
    }

    @Test
    @DirtiesContext
    void testFindAllByItemIdAndStatus() {
        User user = new User(1L,"user1","user1@user.ru");
        userRepository.save(user);

        User user2 = new User(2L,"user2","user2@user.ru");
        userRepository.save(user2);

        User user3 = new User(3L,"user3","user3@user.ru");
        userRepository.save(user3);

        User user4 = new User(4L,"user4","user4@user.ru");
        userRepository.save(user4);

        Item item1 = new Item(1L,"item1","description",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        LocalDateTime date = LocalDateTime.now();

        Booking booking1 = new Booking(item1,user2, date.plusDays(4),date.plusDays(5), BookingStatus.APPROVED);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking(item1,user3, date.plusDays(3),date.plusDays(4), BookingStatus.APPROVED);
        bookingRepository.save(booking2);

        Booking booking3 = new Booking(item1,user4, date.plusDays(1),date.plusDays(4), BookingStatus.REJECTED);
        bookingRepository.save(booking3);

        List<Booking> booking = bookingRepository.findAllByItemIdAndStatus(1L, BookingStatus.APPROVED,
                Pageable.unpaged());
        assertEquals(booking.size(),2);
        assertEquals(booking.get(0).getId(),1L);
        assertEquals(booking.get(1).getId(),2L);
        assertTrue(booking.get(0).getStart().isAfter(booking.get(1).getStart()));
    }

    @Test
    @DirtiesContext
    void testFindAllByBookerIdAndStartBeforeAndEndAfter() {
        User user = new User(1L, "user1", "user1@user.ru");
        userRepository.save(user);

        User user2 = new User(2L, "user2", "user2@user.ru");
        userRepository.save(user2);

        Item item1 = new Item(1L, "item1", "description", true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        LocalDateTime date = LocalDateTime.now();

        Booking booking1 = new Booking(item1, user2, date.minusDays(2), date.plusDays(2), BookingStatus.APPROVED);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking(item1, user2, date.plusDays(3), date.plusDays(4), BookingStatus.APPROVED);
        bookingRepository.save(booking2);

        Booking booking3 = new Booking(item1, user2, date.minusDays(4), date.minusDays(3), BookingStatus.REJECTED);
        bookingRepository.save(booking3);

        Booking booking4 = new Booking(item1, user2, date.plusDays(6), date.plusDays(7), BookingStatus.WAITING);
        bookingRepository.save(booking4);

        List<Booking> bookingCurrent = bookingRepository.findAllByOwnerId(1L, Pageable.unpaged());
        assertEquals(bookingCurrent.size(), 4);
        assertTrue(bookingCurrent.get(0).getStart().isAfter(LocalDateTime.now()));
        assertTrue(bookingCurrent.get(0).getEnd().isAfter(LocalDateTime.now()));

        List<Booking> booking = bookingRepository.findAllByBookerIdAndStatus(2L, BookingStatus.WAITING,
                Pageable.unpaged());
        assertEquals(booking.size(), 1);
        assertTrue(booking.get(0).getStart().isAfter(LocalDateTime.now()));

        List<Booking> bookingPast = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(2L,
                LocalDateTime.now(), Pageable.unpaged());
        assertEquals(bookingPast.size(), 1);
        assertTrue(bookingPast.get(0).getEnd().isAfter(LocalDateTime.now()));

        List<Booking> bookingRejected = bookingRepository.findAllByItemIdAndStatus(2L, BookingStatus.REJECTED,
                Pageable.unpaged());
        assertEquals(bookingRejected.size(), 0);
    }

    @Test
    @DirtiesContext
     void testFindBookingOwnerAllAndCurrentAndFutureAndPast() {
        User user = new User(1L,"user1","user1@user.ru");
        userRepository.save(user);

        User user2 = new User(2L,"user2","user2@user.ru");
        userRepository.save(user2);

        User user3 = new User(3L,"user3","user3@user.ru");
        userRepository.save(user3);

        User user4 = new User(4L,"user4","user4@user.ru");
        userRepository.save(user4);

        Item item1 = new Item(1L,"item1","description",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        LocalDateTime date = LocalDateTime.now();

        Booking booking1 = new Booking(item1,user2, date.minusDays(2),date.plusDays(2), BookingStatus.APPROVED);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking(item1,user3, date.plusDays(3),date.plusDays(4), BookingStatus.APPROVED);
        bookingRepository.save(booking2);

        Booking booking3 = new Booking(item1,user4, date.minusDays(4),date.minusDays(3), BookingStatus.REJECTED);
        bookingRepository.save(booking3);

        Booking booking4 = new Booking(item1,user3, date.plusDays(6), date.plusDays(7), BookingStatus.WAITING);
        bookingRepository.save(booking4);

        List<Booking> bookingCurrent = bookingRepository.findAllByOwnerId(1L,Pageable.unpaged());
        assertEquals(bookingCurrent.size(),4);
        assertTrue(bookingCurrent.get(0).getStart().isAfter(LocalDateTime.now()));
        assertTrue(bookingCurrent.get(0).getEnd().isAfter(LocalDateTime.now()));

        List<Booking> booking = bookingRepository.findAllByOwnerIdAndEndBefore(1L,LocalDateTime.now(),
                Pageable.unpaged());
        assertEquals(booking.size(),1);
        assertTrue(booking.get(0).getStart().isBefore(LocalDateTime.now()));

        List<Booking> bookingPast = bookingRepository.findAllByOwnerIdAndStartAfter(1L, LocalDateTime.now(),
                Pageable.unpaged());
        assertEquals(bookingPast.size(),2);
        assertTrue(bookingPast.get(0).getEnd().isAfter(LocalDateTime.now()));

        List<Booking> bookingRejected = bookingRepository.findAllByBookerIdAndStatus(1L,BookingStatus.REJECTED,
                Pageable.unpaged());
        assertEquals(bookingPast.size(),2);
    }

    @Test
    @DirtiesContext
     void testUpdateStatus() {
        User user = userRepository.save(new User(1L,"user1","user1@user.ru"));
        User user2 = userRepository.save(new User(2L,"user2","user2@user.ru"));

        Item item1 = new Item(1L,"item1","description",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        LocalDateTime date = LocalDateTime.now();
        Booking booking = bookingRepository.save(new Booking(item1,user2,
                date.minusDays(2),date.plusDays(2), BookingStatus.WAITING));

        Booking bookingF = bookingRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found"));
        assertEquals(bookingF.getStatus(),BookingStatus.WAITING);
        bookingRepository.updateStatus(BookingStatus.REJECTED, bookingF.getId());
    }
}