package scala_style.option;

import org.junit.jupiter.api.Test;

import static scala_style.ExceptionUtils.expect;
import static scala_style.Option.ERROR_SOME_OF_NULL;
import static scala_style.Some.Some;

class SomeTest {

    @Test
    void isNotEmpty() {
        assert !Some(5).isEmpty();
    }

    @Test
    void isDefined() {
        assert Some(5).isDefined();
    }

    @Test
    void getReturnsValue() {
        assert Some(5).get().equals(5);
    }

    @Test
    void someOfNullThrowsIllegalArgumentException() {
        expect(() -> Some(null),
                IllegalArgumentException.class,
                ERROR_SOME_OF_NULL);
    }

}
