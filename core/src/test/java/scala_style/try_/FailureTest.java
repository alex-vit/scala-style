package scala_style.try_;

import org.junit.jupiter.api.Test;
import scala_style.None;
import scala_style.function.Supplier;
import scala_style.util.Failure;
import scala_style.util.Left;
import scala_style.util.Success;
import scala_style.util.Try;

import static org.junit.jupiter.api.Assertions.*;
import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Failure.Failure;
import static scala_style.util.Try.*;

class FailureTest {

    private static final String npeMessage = "NPE";
    private static final Try<Integer> tryWithNpe = Try((Supplier<Integer>) () -> {
        throw new NullPointerException(npeMessage);
    });

    @Test
    void failureOfNullThrowsException() {
        expect(() -> Failure(null), IllegalArgumentException.class, ERROR_FAILURE_OF_NULL);
    }

    @Test
    void isFailure() {
        assertTrue(tryWithNpe.isFailure());
    }

    @Test
    void isNotSuccess() {
        assertFalse(tryWithNpe.isSuccess());
    }

    @Test
    void getThrowsException() {
        expect(tryWithNpe::get, RuntimeException.class, ERROR_FAILURE_DOT_GET,
                NullPointerException.class);
    }

    @Test
    void failedReturnsSuccessOfException() {
        Try<Throwable> failed = tryWithNpe.failed();

        assertTrue(failed instanceof Success<?>);
        assertTrue(failed.get() instanceof NullPointerException);
    }

    @Test
    void getOrElseReturnsDefault() {
        assertEquals(tryWithNpe.getOrElse(String.class, () -> ""), "");
    }

    @Test
    void orElseReturnsDefault() {
        assertEquals(tryWithNpe.orElse(String.class, () -> Try(() -> "")).get(), "");
    }

    @Test
    void mapReturnsFailure() {
        assertTrue(tryWithNpe.map(i -> 5f) instanceof Failure<?>);
    }

    @Test
    void flatMapReturnsFailure() {
        assertTrue(tryWithNpe.flatMap(i -> Try(() -> 5f)) instanceof Failure<?>);
    }

    @Test
    void foldAppliesFA() {
        assertEquals(tryWithNpe.fold(Throwable::toString, i -> i.toString()),
                "java.lang.NullPointerException: NPE");
    }

    @Test
    void successfulRecoverWithReturnsSuccess() {
        Try<String> successfulRecoverWith = tryWithNpe.recoverWith(String.class, e -> Try(e::getMessage));

        assert successfulRecoverWith instanceof Success<?>;
        assert successfulRecoverWith.get().equals(npeMessage);
    }

    @Test
    void failedRecoverWithReturnsFailure() {
        Try<String> failedRecoverWith = tryWithNpe.recoverWith(String.class, e -> Try(() -> {
            throw new IllegalArgumentException();
        }));

        assert failedRecoverWith instanceof Failure<?>;
        assert failedRecoverWith.failed().get() instanceof IllegalArgumentException;
    }

    @Test
    void successfulRecoverReturnsSuccess() {
        Try<Integer> successfulRecover = tryWithNpe.recover(Integer.class, e -> 5);

        assert successfulRecover instanceof Success<?>;
        assert successfulRecover.get().equals(5);
    }

    @Test
    void failedRecoverReturnsFailure() {
        Try<String> failedRecover = tryWithNpe.recover(String.class, e -> {
            throw new IllegalArgumentException();
        });

        assert failedRecover instanceof Failure<?>;
        assert failedRecover.failed().get() instanceof IllegalArgumentException;
    }

    @Test
    void toOptionReturnsNone() {
        assert tryWithNpe.toOption() instanceof None<?>;
    }

    @Test
    void toEitherReturnsLeft() {
        assert tryWithNpe.toEither() instanceof Left<?, ?>;
    }

}
