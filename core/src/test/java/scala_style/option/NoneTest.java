package scala_style.option;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import scala_style.None;

import java.util.NoSuchElementException;

import static scala_style.ExceptionUtils.expect;
import static scala_style.None.None;
import static scala_style.Option.ERROR_NONE_GET;

public class NoneTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isEmpty() {
        assert None().isEmpty();
    }

    @Test
    public void isNotDefined() {
        assert !None().isDefined();
    }

    @Test
    public void getThrowsNoSuchElementException() {
        expect(exception, NoSuchElementException.class, ERROR_NONE_GET);

        None().get();
    }

    @Test
    public void canCast() {
        None<Integer> none = None();
        //noinspection unchecked,unused
        None<String> noneOfNumber = (None<String>) ((None<?>) none);
    }

}
