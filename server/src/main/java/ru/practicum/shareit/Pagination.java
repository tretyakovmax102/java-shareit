package ru.practicum.shareit;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.exception.ValidationException;

@UtilityClass
public class Pagination {
    public static Pageable setPageable(Long from, Long size) {
        if (from == null || size == null) {
            return Pageable.unpaged();
        } else if (from >= 0 && size > 0) {
            long page = (from + 1) / size;
            long pageLast = (from + 1) % size;
            if (pageLast > 0) {
                return PageRequest.of((int) page, size.intValue());
            } else {
                return PageRequest.of((int) page - 1, size.intValue());
            }
        } else {
            throw new ValidationException("setPageable not success");
        }
    }
}
