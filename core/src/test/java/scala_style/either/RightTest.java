package scala_style.either;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Either.ERROR_RIGHT_OF_NULL;
import static scala_style.util.Right.Right;

class RightTest {

    @Test
    void isRight() {
        assertTrue(Right(5).isRight());
    }

    @Test
    void isNotLeft() {
        assertFalse(Right(5).isLeft());
    }

    @Test
    void rightOfNullThrowsIllegalArgumentException() {
        expect(() -> Right(null), IllegalArgumentException.class, ERROR_RIGHT_OF_NULL);
    }

}
