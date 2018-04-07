package scala_style.function;

public interface Predicate<A> extends Function<A, Boolean> {
    default Boolean test(A a) {
        return apply(a);
    }
}