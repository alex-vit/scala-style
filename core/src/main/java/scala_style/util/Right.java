package scala_style.util;

public final class Right<A, B> extends Either<A, B> {

    final B value;

    public Right(B value) {
        if (value == null) throw new IllegalArgumentException(ERROR_RIGHT_OF_NULL);
        this.value = value;
    }

    @SuppressWarnings("MethodNameSameAsClassName")
    public static <A, B> Right<A, B> Right(B value) {
        return new Right<>(value);
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

}
