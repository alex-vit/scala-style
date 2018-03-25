package scala_style.try_;

import org.junit.jupiter.api.Test;
import scala_style.Option;
import scala_style.Some;
import scala_style.util.Either;
import scala_style.util.Right;
import scala_style.util.Try;

import static scala_style.ExceptionUtils.expect;
import static scala_style.Option.ERROR_NOT_SUPERTYPE;
import static scala_style.util.Success.Success;
import static scala_style.util.Try.*;

class SuccessTest {

    private static final Try<Integer> try5 = Try(() -> 5);

    @Test
    void successOfNullThrowsException() {
        expect(() -> Success(null),
                IllegalArgumentException.class, ERROR_SUCCESS_OF_NULL);
    }

    @Test
    void isNotFailure() {
        assert !Success(5).isFailure();
    }

    @Test
    void isSuccess() {
        assert Success(5).isSuccess();
    }

    @Test
    void getReturnsValue() {
        assert Success(5).get().equals(5);
    }

    @Test
    void failedReturnsFailure() {
        expect(() -> Success(5).failed().get(),
                RuntimeException.class, ERROR_FAILURE_DOT_GET,
                UnsupportedOperationException.class, ERROR_SUCCESS_DOT_FAILED);
    }

    @Test
    void getOrElseReturnsValue() {
        assert try5.getOrElse(Number.class, () -> 7).equals(5);
    }

    @Test
    void getOrElseWithUnrelatedTypesThrowsIllegalArgumentException() {
        expect(() -> try5.getOrElse(String.class, () -> ""),
                IllegalArgumentException.class, ERROR_NOT_SUPERTYPE);
    }

    @Test
    void orElseReturnsValue() {
        assert try5.orElse(Number.class, () -> Try(() -> 7)).get().equals(5);
    }

    @Test
    void orElseWithUnrelatedTypesThrowsIllegalArgumentException() {
        expect(() -> try5.orElse(String.class, () -> Try(() -> "")),
                IllegalArgumentException.class, ERROR_NOT_SUPERTYPE);
    }

    @Test
    void mapAppliesF() {
        assert try5.map(i -> String.valueOf(i * 2)).get().equals("10");
    }

    @Test
    void flatMapAppliesF() {
        assert try5.flatMap(i -> Try(() -> String.valueOf(i * 2))).get().equals("10");
    }

    @Test
    void foldAppliesFB() {
        assert try5.fold(e -> 7, i -> "").equals("");
    }

    @Test
    void recoverWithReturnsValue() {
        assert try5.recoverWith(Number.class, i -> Try(() -> 7)).get().equals(5);
    }

    @Test
    void recoverReturnsValue() {
        assert try5.recover(Number.class, i -> 7).get().equals(5);
    }

    @Test
    void toOptionReturnsSome() {
        Option<Integer> option = try5.toOption();

        assert option instanceof Some<?>;
        assert try5.get().equals(option.get());
    }

    @Test
    void toEitherReturnsRight() {
        Either<Throwable, Integer> either = try5.toEither();

        assert either instanceof Right<?, ?>;
        assert try5.get().equals(either.right());
    }

}
