package scala_style.option;

import org.junit.jupiter.api.Test;
import scala_style.None;

import java.util.NoSuchElementException;

import static scala_style.ExceptionUtils.expect;
import static scala_style.None.None;
import static scala_style.Option.ERROR_NONE_GET;

class NoneTest {

    @Test
    void isEmpty() {
        assert None().isEmpty();
    }

    @Test
    void isNotDefined() {
        assert !None().isDefined();
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
