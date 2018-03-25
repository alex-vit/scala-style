package scala_style.util;

import scala_style.Option;

import java.util.function.Function;
import java.util.function.Supplier;

import static scala_style.Option.ERROR_NOT_SUPERTYPE;
import static scala_style.Some.Some;
import static scala_style.util.Failure.Failure;
import static scala_style.util.Generics.isSuperType;
import static scala_style.util.Right.Right;

public final class Success<A> extends Try<A> {

    private final A value;

    public Success(A value) {
        if (value == null) throw new IllegalArgumentException(ERROR_SUCCESS_OF_NULL);
        this.value = value;
    }

    @SuppressWarnings("MethodNameSameAsClassName")
    public static <A> Success<A> Success(A value) {
        return new Success<>(value);
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public A get() {
        return value;
    }

    @Override
    public Try<Throwable> failed() {
        return Failure(new UnsupportedOperationException(ERROR_SUCCESS_DOT_FAILED));
    }

    @Override
    public <B> B getOrElse(Class<B> superType, Supplier<B> default_) {
        if (!isSuperType(superType, get().getClass())) throw new IllegalArgumentException(ERROR_NOT_SUPERTYPE);
        return superType.cast(get());
    }

    @Override
    public <B> Try<B> orElse(Class<B> superType, Supplier<Try<B>> default_) {
        if (!isSuperType(superType, get().getClass())) throw new IllegalArgumentException(ERROR_NOT_SUPERTYPE);
        return Success(superType.cast(get()));
    }

    @Override
    public <B> Try<B> map(Function<A, B> f) {
        return Success(f.apply(get()));
    }

    @Override
    public <B> Try<B> flatMap(Function<A, Try<B>> f) {
        return f.apply(get());
    }

    @Override
    public <B> B fold(Function<Throwable, B> fa, Function<A, B> fb) {
        try {
            return fb.apply(get());
        } catch (Throwable e) {
            return fa.apply(e);
        }
    }

    @Override
    public <B> Try<B> recoverWith(Class<B> superType, Function<Throwable, Try<B>> f) {
        if (!isSuperType(superType, get().getClass())) throw new IllegalArgumentException(ERROR_NOT_SUPERTYPE);
        return Success(superType.cast(get()));
    }

    @Override
    public <B> Try<B> recover(Class<B> superType, Function<Throwable, B> f) {
        if (!isSuperType(superType, get().getClass())) throw new IllegalArgumentException(ERROR_NOT_SUPERTYPE);
        return Success(superType.cast(get()));
    }

    @Override
    public Option<A> toOption() {
        return Some(get());
    }

    @Override
    public Either<Throwable, A> toEither() {
        return Right(get());
    }

}
