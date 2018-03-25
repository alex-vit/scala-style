package scala_style.either;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static scala_style.util.Either.ERROR_RIGHT_OF_NULL;
import static scala_style.util.Right.Right;

class RightTest {

    @Test
    void isRight() {
        assert Right(5).isRight();
    }

    @Test
    void isNotLeft() {
        assert !Right(5).isLeft();
    }

    @Test
    void rightOfNullThrowsIllegalArgumentException() {
        IllegalArgumentException exception = expectThrows(IllegalArgumentException.class, () -> Right(null));
        assertEquals(exception.getMessage(), ERROR_RIGHT_OF_NULL);
    }

}
