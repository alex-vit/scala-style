package scala_style.util;

import java.util.NoSuchElementException;

public abstract class Either<A, B> {

    public static final String ERROR_LEFT_OF_NULL = "Left(null)";
    public static final String ERROR_RIGHT_OF_NULL = "Right(null)";
    public static final String ERROR_RIGHT_DOT_LEFT = "Right.left";
    public static final String ERROR_LEFT_DOT_RIGHT = "Left.right";

    public abstract boolean isLeft();

    public abstract boolean isRight();

    public final A left() {
        return new LeftProjection<>(this).get();
    }

    public final B right() {
        return new RightProjection<>(this).get();
    }

    private static final class LeftProjection<A, B> extends Either<A, B> {

        private final Either<A, B> e;

        LeftProjection(Either<A, B> e) {
            this.e = e;
        }

        A get() {
            if (e instanceof Left<?, ?>) return ((Left<A, B>) e).value;
            else throw new NoSuchElementException(ERROR_RIGHT_DOT_LEFT);
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return false;
        }
    }

    private static final class RightProjection<A, B> extends Either<A, B> {

        private final Either<A, B> e;

        RightProjection(Either<A, B> e) {
            this.e = e;
        }

        B get() {
            if (e instanceof Right<?, ?>) return ((Right<A, B>) e).value;
            else throw new NoSuchElementException(ERROR_LEFT_DOT_RIGHT);
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return false;
        }
    }

}
