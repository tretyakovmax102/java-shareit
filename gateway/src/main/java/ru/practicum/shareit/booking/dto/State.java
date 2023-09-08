package ru.practicum.shareit.booking.dto;

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
        } catch (Exception e) {
            throw new ExceptionForUnsupport("Unknown state: UNSUPPORTED_STATUS");
        }
    }
}