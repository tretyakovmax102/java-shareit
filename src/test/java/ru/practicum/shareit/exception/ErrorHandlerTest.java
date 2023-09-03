package ru.practicum.shareit.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ErrorHandlerTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void handleExceptionForUnsupportTest() {
        ErrorResponse response = errorHandler.handleExceptionForUnsupport(new ExceptionForUnsupport("ExceptionForUnsupport"));
        assertEquals(response.getError(),"ExceptionForUnsupport");
    }

    @Test
    void handleNotFoundExceptionTest() {
        ErrorResponse response = errorHandler.handleNotFoundException(new NotFoundException("NotFoundException"));
        assertEquals(response.getError(),"NotFoundException: NotFoundException");
    }

    @Test
    void handleValidationException() {
        ErrorResponse response = errorHandler.handleValidationException(new ValidationException("ValidationException"));
        assertEquals(response.getError(),"ValidationException: ValidationException");
    }

    @Test
    void handleForbiddenExceptionTest() {
        ErrorResponse response = errorHandler.handleForbiddenException(new ForbiddenException("ForbiddenException"));
        assertEquals(response.getError(),"ForbiddenException: ForbiddenException");
    }

    @Test
    void handleThrowableTest() {
        ErrorResponse response = errorHandler.handleThrowable(new Throwable("Throwable"));
        assertEquals(response.getError(),"Throwable");
    }


}