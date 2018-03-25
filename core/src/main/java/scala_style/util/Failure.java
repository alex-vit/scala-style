package scala_style.util;

import scala_style.Option;

import java.util.function.Function;
import java.util.function.Supplier;

import static scala_style.None.None;
import static scala_style.util.Left.Left;
import static scala_style.util.Success.Success;

public final class Failure<A> extends Try<A> {

    private final Throwable exception;

    public Failure(Throwable exception) {
        if (exception == null) throw new IllegalArgumentException(ERROR_FAILURE_OF_NULL);
        this.exception = exception;
    }

    @SuppressWarnings("MethodNameSameAsClassName")
    public static <A> Failure<A> Failure(Throwable e) {
        return new Failure<>(e);
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public A get() {
        throw new RuntimeException(ERROR_FAILURE_DOT_GET, exception);
    }

    @Override
    public Try<Throwable> failed() {
        return Success(exception);
    }

    @Override
    public <B> B getOrElse(Class<B> type, Supplier<B> default_) {
        return default_.get();
    }

    @Override
    public <B> Try<B> orElse(Class<B> type, Supplier<Try<B>> default_) {
        return default_.get();
    }

    @Override
    public <B> Try<B> map(Function<A, B> f) {
        //noinspection unchecked
        return (Try<B>) this;
    }

    @Override
    public <B> Try<B> flatMap(Function<A, Try<B>> f) {
        //noinspection unchecked
        return (Try<B>) this;
    }

    @Override
    public <B> B fold(Function<Throwable, B> fa, Function<A, B> fb) {
        return fa.apply(exception);
    }

    @Override
    public <B> Try<B> recoverWith(Class<B> type, Function<Throwable, Try<B>> f) {
        try {
            return f.apply(exception);
        } catch (Throwable e) {
            return Failure(e);
        }
    }

    @Override
    public <B> Try<B> recover(Class<B> type, Function<Throwable, B> f) {
        try {
            return Success(f.apply(exception));
        } catch (Throwable e) {
            return Failure(e);
        }
    }

    @Override
    public Option<A> toOption() {
        return None();
    }

    @Override
    public Either<Throwable, A> toEither() {
        return Left(exception);
    }

}
