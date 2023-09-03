package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaginationTest {

    @Test
    public void setPageableTest() {
        assertThrows(ValidationException.class, () -> Pagination.setPageable(-10L,0L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(0L,-10L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(-10L,-10L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(0L,0L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(-10L,5L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(5L,-10L));

        Pageable pageable1 = Pagination.setPageable(1L,1L);
        assertEquals(pageable1.getPageNumber(),1);
        assertEquals(pageable1.getPageSize(),1);

        Pageable pageable2 = Pagination.setPageable(4L,3L);
        assertEquals(pageable2.getPageNumber(),1);
        assertEquals(pageable2.getPageSize(),3);
    }
}