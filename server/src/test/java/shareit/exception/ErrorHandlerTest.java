package shareit.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ErrorHandlerTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    void testSetUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void testHandleExceptionForUnsuppor() {
        ErrorResponse response = errorHandler.handleExceptionForUnsupport(new ExceptionForUnsupport("ExceptionForUnsupport"));
        assertEquals(response.getError(),"ExceptionForUnsupport");
    }

    @Test
    void testHandleNotFoundException() {
        ErrorResponse response = errorHandler.handleNotFoundException(new NotFoundException("NotFoundException"));
        assertEquals(response.getError(),"NotFoundException: NotFoundException");
    }

    @Test
    void testHandleValidationException() {
        ErrorResponse response = errorHandler.handleValidationException(new ValidationException("ValidationException"));
        assertEquals(response.getError(),"ValidationException: ValidationException");
    }

    @Test
    void testHandleForbiddenException() {
        ErrorResponse response = errorHandler.handleForbiddenException(new ForbiddenException("ForbiddenException"));
        assertEquals(response.getError(),"ForbiddenException: ForbiddenException");
    }

    @Test
    void testHandleThrowable() {
        ErrorResponse response = errorHandler.handleThrowable(new Throwable("Throwable"));
        assertEquals(response.getError(),"Throwable");
    }


}