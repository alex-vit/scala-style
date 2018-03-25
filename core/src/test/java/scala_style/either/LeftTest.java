package scala_style.either;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Either.ERROR_LEFT_OF_NULL;
import static scala_style.util.Left.Left;

public class LeftTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isLeft() {
        assert Left(5).isLeft();
    }

    @Test
    public void isNotRight() {
        assert !Left(5).isRight();
    }

    @Test
    public void leftOfNullThrowsIllegalArgumentException() {
        expect(exception, IllegalArgumentException.class, ERROR_LEFT_OF_NULL);

        Left(null);
    }

}
