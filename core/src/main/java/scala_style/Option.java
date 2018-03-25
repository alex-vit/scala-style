package scala_style;

import com.sun.istack.internal.Nullable;
import scala_style.util.Either;
import scala_style.util.Left;
import scala_style.util.Right;

import java.util.function.Function;
import java.util.function.Supplier;

import static scala_style.None.None;
import static scala_style.Some.Some;
import static scala_style.util.Generics.isSuperType;

public abstract class Option<A> {

    public static final String ERROR_NONE_GET = "None.get";
    public static final String ERROR_SOME_OF_NULL = "Some(null)";
    public static final String ERROR_NOT_SUPERTYPE = "B must be a supertype";

    @SuppressWarnings({"MethodNameSameAsClassName", "WeakerAccess"})
    public static <A> Option<A> Option(@Nullable A value) {
        return (value == null) ? None() : Some(value);
    }

    public static <A> Option<A> empty() {
        return None();
    }

    public static <A> Option<A> when(boolean condition, Supplier<A> a) {
        return (condition) ? Some(a.get()) : None();
    }

    public static <A> Option<A> unless(boolean condition, Supplier<A> a) {
        return when(!condition, a);
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    public abstract boolean isEmpty();

    public final boolean isDefined() {
        return !isEmpty();
    }

    public abstract A get();

    public final <B> B getOrElse(Class<B> superType, Supplier<B> default_) {
        final Class<?> selfType = (isEmpty()) ? superType : get().getClass();
        if (!isSuperType(superType, selfType)) throw new IllegalArgumentException(ERROR_NOT_SUPERTYPE);
        return (isEmpty()) ? default_.get() : superType.cast(get());
    }

    @Nullable
    public final A orNull() {
        return (isEmpty()) ? null : get();
    }

    public final <B> Option<B> orElse(Class<B> superType, Supplier<Option<B>> a) {
        final Class<?> selfType = (isEmpty()) ? superType : get().getClass();
        if (!isSuperType(superType, selfType)) throw new IllegalArgumentException(ERROR_NOT_SUPERTYPE);
        return (isEmpty()) ? a.get() : Option(superType.cast(get()));
    }

    public final <B> Option<B> map(Function<A, B> f) {
        return (isEmpty()) ? None() : Some(f.apply(get()));
    }

    public final <B> B fold(Supplier<B> ifEmpty, Function<A, B> f) {
        return (isEmpty()) ? ifEmpty.get() : f.apply(get());
    }

    public final <B> Option<B> flatMap(Function<A, Option<B>> f) {
        return (isEmpty()) ? None() : f.apply(get());
    }

    public final <X> Either<A, X> toLeft(Supplier<X> right) {
        return (isEmpty()) ? Right.Right(right.get()) : Left.Left(get());
    }

    public final <X> Either<X, A> toRight(Supplier<X> left) {
        return (isEmpty()) ? Left.Left(left.get()) : Right.Right(get());
    }

}
