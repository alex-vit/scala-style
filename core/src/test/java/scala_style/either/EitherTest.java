package scala_style.either;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Either.ERROR_LEFT_DOT_RIGHT;
import static scala_style.util.Either.ERROR_RIGHT_DOT_LEFT;
import static scala_style.util.Left.Left;
import static scala_style.util.Right.Right;

public class EitherTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void leftDotLeftReturnsValue() {
        assert Left(5).left().equals(5);
    }

    @Test
    public void leftDotRightThrowsNoSuchElementException() {
        expect(exception, NoSuchElementException.class, ERROR_LEFT_DOT_RIGHT);

        Left(5).right();
    }

    @Test
    public void rightDotRighttReturnsValue() {
        assert Right(5).right().equals(5);
    }

    @Test
    public void rightDotLeftThrowsNoSuchElementException() {
        expect(exception, NoSuchElementException.class, ERROR_RIGHT_DOT_LEFT);

        Right(5).left();
    }

}
