package scala_style.either;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Either.ERROR_LEFT_DOT_RIGHT;
import static scala_style.util.Either.ERROR_RIGHT_DOT_LEFT;
import static scala_style.util.Left.Left;
import static scala_style.util.Right.Right;

class EitherTest {

    @Test
    @DisplayName("Left.left returns value")
    void leftDotLeftReturnsValue() {
        assert Left(5).left().equals(5);
    }

    @Test
    void leftDotRightThrowsNoSuchElementException() {
        expect(() -> Left(5).right(), NoSuchElementException.class, ERROR_LEFT_DOT_RIGHT);
    }

    @Test
    void rightDotRighttReturnsValue() {
        assert Right(5).right().equals(5);
    }

    @Test
    void rightDotLeftThrowsNoSuchElementException() {
        expect(() -> Right(5).left(), NoSuchElementException.class, ERROR_RIGHT_DOT_LEFT);
    }

}
