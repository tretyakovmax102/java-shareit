package shareit.booking.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.exception.ExceptionForUnsupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StateTest {

    @Test
    void getState() {
        String str = "ALL";
        State state = State.getState(str);
        assertEquals(State.ALL, state);
        str = "CURRENT";
        state = State.getState(str);
        assertEquals(State.CURRENT, state);
        assertThrows(ExceptionForUnsupport.class, () -> State.getState("asd"));
        assertEquals(State.ALL, State.getState(null));
        assertEquals(State.ALL, State.getState(""));

    }
}