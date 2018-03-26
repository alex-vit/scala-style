package scala_style;

public class Some<T> extends Option<T> {

    private final T value;

    public Some(T value) {
        if (value == null) throw new IllegalArgumentException(ERROR_SOME_OF_NULL);
        this.value = value;
    }

    @SuppressWarnings("MethodNameSameAsClassName")
    public static <A> Some<A> Some(A value) {
        return new Some<>(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && Some.class.isAssignableFrom(obj.getClass())) {
            final Some<?> some = (Some<?>) obj;
            return this.get().equals(some.get());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return get().hashCode();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T get() {
        return value;
    }

}
