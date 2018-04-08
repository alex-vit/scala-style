package scala_style.option;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import scala_style.None;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scala_style.ExceptionUtils.expect;
import static scala_style.None.None;
import static scala_style.Option.ERROR_NONE_GET;

class NoneTest {

    @Test
    void isEmpty() {
        assertTrue(None().isEmpty());
    }

    @Test
    void isNotDefined() {
        assertFalse(None().isDefined());
    }

    @Test
    void getThrowsNoSuchElementException() {
        expect(() -> None().get(),
                NoSuchElementException.class,
                ERROR_NONE_GET);
    }

    @Test
    void canCast() {
        None<Integer> none = None();
        //noinspection unchecked,unused
        None<String> noneOfNumber = (None<String>) ((None<?>) none);
    }

}
