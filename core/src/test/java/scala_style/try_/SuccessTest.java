package scala_style.try_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import scala_style.Option;
import scala_style.Some;
import scala_style.util.Either;
import scala_style.util.Right;
import scala_style.util.Try;

import static scala_style.ExceptionUtils.expect;
import static scala_style.Option.ERROR_NOT_SUPERTYPE;
import static scala_style.util.Success.Success;
import static scala_style.util.Try.*;

public class SuccessTest {

    private static final Try<Integer> try5 = Try(() -> 5);
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isNotFailure() {
        assert !Success(5).isFailure();
    }

    @Test
    public void isSuccess() {
        assert Success(5).isSuccess();
    }

    @Test
    public void getReturnsValue() {
        assert Success(5).get().equals(5);
    }

    @Test
    public void failedReturnsFailure() {
        expect(exception, RuntimeException.class, ERROR_FAILURE_DOT_GET,
                UnsupportedOperationException.class, ERROR_SUCCESS_DOT_FAILED);

        Success(5).failed().get();
    }

    @Test
    public void getOrElseReturnsValue() {
        assert try5.getOrElse(Number.class, () -> 7).equals(5);
    }

    @Test
    public void getOrElseWithUnrelatedTypesThrowsIllegalArgumentException() {
        expect(exception, IllegalArgumentException.class, ERROR_NOT_SUPERTYPE);

        try5.getOrElse(String.class, () -> "");
    }

    @Test
    public void orElseReturnsValue() {
        assert try5.orElse(Number.class, () -> Try(() -> 7)).get().equals(5);
    }

    @Test
    public void orElseWithUnrelatedTypesThrowsIllegalArgumentException() {
        expect(exception, IllegalArgumentException.class, ERROR_NOT_SUPERTYPE);

        try5.orElse(String.class, () -> Try(() -> ""));
    }

    @Test
    public void mapAppliesF() {
        assert try5.map(i -> String.valueOf(i * 2)).get().equals("10");
    }

    @Test
    public void flatMapAppliesF() {
        assert try5.flatMap(i -> Try(() -> String.valueOf(i * 2))).get().equals("10");
    }

    @Test
    public void foldAppliesFB() {
        assert try5.fold(e -> 7, i -> "").equals("");
    }

    @Test
    public void recoverWithReturnsValue() {
        assert try5.recoverWith(Number.class, i -> Try(() -> 7)).get().equals(5);
    }

    @Test
    public void recoverReturnsValue() {
        assert try5.recover(Number.class, i -> 7).get().equals(5);
    }

    @Test
    public void toOptionReturnsSome() {
        Option<Integer> option = try5.toOption();

        assert option instanceof Some<?>;
        assert try5.get().equals(option.get());
    }

    @Test
    public void toEitherReturnsRight() {
        Either<Throwable, Integer> either = try5.toEither();

        assert either instanceof Right<?, ?>;
        assert try5.get().equals(either.right());
    }

}
