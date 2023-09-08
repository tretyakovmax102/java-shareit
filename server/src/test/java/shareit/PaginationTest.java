package shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.Pagination;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PaginationTest {

    @Test
    void testSetPageable() {
        assertThrows(ValidationException.class, () -> Pagination.setPageable(-10L,0L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(0L,-10L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(-10L,-10L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(0L,0L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(-10L,5L));
        assertThrows(ValidationException.class, () -> Pagination.setPageable(5L,-10L));

        Pageable pageable1 = Pagination.setPageable(1L,1L);
        assertEquals(1, pageable1.getPageNumber());
        assertEquals(1, pageable1.getPageSize());

        Pageable pageable2 = Pagination.setPageable(4L,3L);
        assertEquals(1, pageable2.getPageNumber());
        assertEquals(3,pageable2.getPageSize());
    }
}