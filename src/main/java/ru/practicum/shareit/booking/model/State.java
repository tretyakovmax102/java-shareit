package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.exception.ExceptionForUnsupport;

public enum State {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static State getState(String text) {
        if ((text == null) || text.isBlank()) {
            return State.ALL;
        }
        try {
            return State.valueOf(text.toUpperCase().trim());
        } catch (RuntimeException e) {
            throw new ExceptionForUnsupport("Unknown state: UNSUPPORTED_STATUS");
        }
    }
}