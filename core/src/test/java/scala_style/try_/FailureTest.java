package scala_style.try_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import scala_style.None;
import scala_style.util.Failure;
import scala_style.util.Left;
import scala_style.util.Success;
import scala_style.util.Try;

import java.util.function.Supplier;

import static scala_style.ExceptionUtils.expect;
import static scala_style.util.Try.ERROR_FAILURE_DOT_GET;
import static scala_style.util.Try.Try;

public class FailureTest {

    private static final String npeMessage = "NPE";
    private static final Try<Integer> tryWithNpe = Try((Supplier<Integer>) () -> {
        throw new NullPointerException(npeMessage);
    });
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void isFailure() {
        assert tryWithNpe.isFailure();
    }

    @Test
    public void isNotSuccess() {
        assert !tryWithNpe.isSuccess();
    }

    @Test
    public void getThrowsException() {
        expect(exception, RuntimeException.class, ERROR_FAILURE_DOT_GET, NullPointerException.class);

        tryWithNpe.get();
    }

    @Test
    public void failedReturnsSuccessOfException() {
        Try<Throwable> failed = tryWithNpe.failed();

        assert failed instanceof Success<?>;
        assert failed.get() instanceof NullPointerException;
    }

    @Test
    public void getOrElseReturnsDefault() {
        assert tryWithNpe.getOrElse(String.class, () -> "").equals("");
    }

    @Test
    public void orElseReturnsDefault() {
        assert tryWithNpe.orElse(String.class, () -> Try(() -> "")).get().equals("");
    }

    @Test
    public void mapReturnsFailure() {
        assert tryWithNpe.map(i -> 5f) instanceof Failure<?>;
    }

    @Test
    public void flatMapReturnsFailure() {
        assert tryWithNpe.flatMap(i -> Try(() -> 5f)) instanceof Failure<?>;
    }

    @Test
    public void foldAppliesFA() {
        assert tryWithNpe.fold(e -> 7, i -> "").equals(7);
    }

    @Test
    public void successfulRecoverWithReturnsSuccess() {
        Try<String> successfulRecoverWith = tryWithNpe.recoverWith(String.class, e -> Try(e::getMessage));

        assert successfulRecoverWith instanceof Success<?>;
        assert successfulRecoverWith.get().equals(npeMessage);
    }

    @Test
    public void failedRecoverWithReturnsFailure() {
        Try<String> failedRecoverWith = tryWithNpe.recoverWith(String.class, e -> Try(() -> {
            throw new IllegalArgumentException();
        }));

        assert failedRecoverWith instanceof Failure<?>;
        assert failedRecoverWith.failed().get() instanceof IllegalArgumentException;
    }

    @Test
    public void successfulRecoverReturnsSuccess() {
        Try<Integer> successfulRecover = tryWithNpe.recover(Integer.class, e -> 5);

        assert successfulRecover instanceof Success<?>;
        assert successfulRecover.get().equals(5);
    }

    @Test
    public void failedRecoverReturnsFailure() {
        Try<String> failedRecover = tryWithNpe.recover(String.class, e -> {
            throw new IllegalArgumentException();
        });

        assert failedRecover instanceof Failure<?>;
        assert failedRecover.failed().get() instanceof IllegalArgumentException;
    }

    @Test
    public void toOptionReturnsNone() {
        assert tryWithNpe.toOption() instanceof None<?>;
    }

    @Test
    public void toEitherReturnsLeft() {
        assert tryWithNpe.toEither() instanceof Left<?, ?>;
    }

}
