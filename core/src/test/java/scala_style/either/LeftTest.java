package scala_style.either;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Either.ERROR_LEFT_OF_NULL;
import static scala_style.util.Left.Left;

class LeftTest {

    @Test
    void isLeft() {
        assertTrue(Left(5).isLeft());
    }

    @Test
    void isNotRight() {
        assertFalse(Left(5).isRight());
    }

    @Test
    void leftOfNullThrowsIllegalArgumentException() {
        expect(() -> Left(null), IllegalArgumentException.class, ERROR_LEFT_OF_NULL);
    }

}
