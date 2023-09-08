package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Modifying
    @Query("UPDATE Booking i SET i.status = :status WHERE i.id = :bookingId")
    void updateStatus(@Param("status") BookingStatus status, @Param("bookingId") Long bookingId);


    List<Booking> findAllByBookerIdOrderByStartDesc(@Param("bookerId") long bookerId, Pageable pageable);

    List<Booking> findAllByBookerIdAndStatus(long bookerId, BookingStatus status, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long bookerId, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long bookerId, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByItemIdAndStatus(long itemId, BookingStatus status, Pageable pageable);

    Optional<Booking> findFirstByItemIdAndBookerIdAndStatusAndEndBefore(long itemId, long bookerId,
                                                                        BookingStatus status, LocalDateTime end);

    @Query(value = "SELECT i FROM Booking i " +
            "WHERE i.booker.id = :bookerId " +
            "AND i.start < :dateTime " +
            "AND i.end > :dateTime " +
            "ORDER BY i.start DESC")
    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfter(@Param("bookerId")long bookerId,
                                                             @Param("dateTime")LocalDateTime dateTime,
                                                             Pageable pageable);

    @Query(value = "SELECT i FROM Booking i " +
            "join fetch i.item AS a " +
            "join fetch a.owner AS o " +
            " WHERE o.id = :ownerId " +
            "ORDER BY i.start DESC")
    List<Booking> findAllByOwnerId(@Param("ownerId") Long ownerId,
                                   Pageable pageable);

    @Query(value = "SELECT i " +
            "FROM Booking i " +
            "join fetch i.item AS a join fetch a.owner AS o " +
            " WHERE o.id = :ownerId " +
            "AND i.status = :status " +
            "ORDER BY i.start DESC")
    List<Booking> findAllByOwnerIdAndStatus(@Param("ownerId") Long ownerId,
                                            @Param("status") BookingStatus status,
                                            Pageable pageable);

    @Query(value = "SELECT i FROM Booking i " +
            "join fetch i.item AS a " +
            "join fetch a.owner AS o " +
            "WHERE o.id = :ownerId " +
            "AND i.start > :dateTime  " +
            "ORDER BY i.start DESC")
    List<Booking> findAllByOwnerIdAndStartAfter(@Param("ownerId") Long ownerId,
                                                @Param("dateTime") LocalDateTime dateTime,
                                                Pageable pageable);

    @Query(value = "SELECT i FROM Booking i " +
            "join fetch i.item AS a " +
            "join fetch a.owner AS o " +
            " WHERE o.id = :ownerId " +
            "AND i.end < :dateTime  " +
            "ORDER BY i.start DESC")
    List<Booking> findAllByOwnerIdAndEndBefore(@Param("ownerId") Long ownerId,
                                               @Param("dateTime") LocalDateTime dateTime,
                                               Pageable pageable);

    @Query(value = "SELECT i FROM Booking i " +
            "join fetch i.item AS a " +
            "join fetch a.owner AS o " +
            " WHERE o.id = :ownerId " +
            "AND i.start < :dateTime " +
            "AND i.end > :dateTime " +
            "ORDER BY i.start DESC")
    List<Booking> findAllByOwnerIdAndStartBeforeAndEndAfter(@Param("ownerId") Long ownerId,
                                                            @Param("dateTime") LocalDateTime dateTime,
                                                            Pageable pageable);

}
