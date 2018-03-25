package scala_style.either;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Either.ERROR_RIGHT_OF_NULL;
import static scala_style.util.Right.Right;

public class RightTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isRight() {
        assert Right(5).isRight();
    }

    @Test
    public void isNotLeft() {
        assert !Right(5).isLeft();
    }

    @Test
    public void rightOfNullThrowsIllegalArgumentException() {
        expect(exception, IllegalArgumentException.class, ERROR_RIGHT_OF_NULL);

        Right(null);
    }

}
