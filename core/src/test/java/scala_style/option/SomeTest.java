package scala_style.option;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static scala_style.ExceptionUtils.expect;
import static scala_style.Option.ERROR_SOME_OF_NULL;
import static scala_style.Some.Some;

public class SomeTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isNotEmpty() {
        assert !Some(5).isEmpty();
    }

    @Test
    public void isDefined() {
        assert Some(5).isDefined();
    }

    @Test
    public void getReturnsValue() {
        assert Some(5).get().equals(5);
    }

    @Test
    public void someOfNullThrowsIllegalArgumentException() {
        expect(exception, IllegalArgumentException.class, ERROR_SOME_OF_NULL);
        //noinspection ConstantConditions
        Some(null);
    }

}
