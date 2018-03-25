package scala_style.either;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static scala_style.util.Either.ERROR_LEFT_DOT_RIGHT;
import static scala_style.util.Either.ERROR_RIGHT_DOT_LEFT;
import static scala_style.util.Left.Left;
import static scala_style.util.Right.Right;

@RunWith(JUnitPlatform.class)
class EitherTest {

    @Test
    @DisplayName("Left.left returns value")
    void leftDotLeftReturnsValue() {
        assert Left(5).left().equals(5);
    }

    @Test
    void leftDotRightThrowsNoSuchElementException() {
        NoSuchElementException exception = expectThrows(NoSuchElementException.class, () -> Left(5).right());
        assertEquals(exception.getMessage(), ERROR_LEFT_DOT_RIGHT);
    }

    @Test
    void rightDotRighttReturnsValue() {
        assert Right(5).right().equals(5);
    }

    @Test
    void rightDotLeftThrowsNoSuchElementException() {
        NoSuchElementException exception = expectThrows(NoSuchElementException.class, () -> Right(5).left());
        assertEquals(exception.getMessage(), ERROR_RIGHT_DOT_LEFT);
    }

}
