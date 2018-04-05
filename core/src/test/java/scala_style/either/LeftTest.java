package scala_style.either;

import org.junit.jupiter.api.Test;

import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Either.ERROR_LEFT_OF_NULL;
import static scala_style.util.Left.Left;

class LeftTest {

    @Test
    void isLeft() {
        assert Left(5).isLeft();
    }

    @Test
    void isNotRight() {
        assert !Left(5).isRight();
    }

    @Test
    void leftOfNullThrowsIllegalArgumentException() {
        expect(() -> Left(null), IllegalArgumentException.class, ERROR_LEFT_OF_NULL);
    }

}
