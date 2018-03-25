package scala_style;

import java.util.NoSuchElementException;

public final class None<T> extends Option<T> {

    @SuppressWarnings("MethodNameSameAsClassName")
    public static <A> None<A> None() {
        return new None<>();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && None.class.isAssignableFrom(obj.getClass());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T get() {
        throw new NoSuchElementException(ERROR_NONE_GET);
    }

}
