package scala_style.function;

@FunctionalInterface
public interface Function<A, B> {
    B apply(A t);
}