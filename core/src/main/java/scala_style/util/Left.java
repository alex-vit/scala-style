package scala_style.util;

public final class Left<A, B> extends Either<A, B> {

    final A value;

    public Left(A value) {
        if (value == null) throw new IllegalArgumentException(ERROR_LEFT_OF_NULL);
        this.value = value;
    }

    @SuppressWarnings("MethodNameSameAsClassName")
    public static <A, B> Left<A, B> Left(A value) {
        return new Left<>(value);
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }
}
