package scala_style;

import org.junit.jupiter.api.Test;

import static scala_style.util.Generics.isSuperType;

class GenericsTest {

    @Test
    void numberAndIntegerShouldReturnTrue() {
        assert isSuperType(Number.class, Integer.class);
    }

    @Test
    void stringAndIntegerShouldReturnFalse() {
        assert !isSuperType(String.class, Integer.class);
    }

}
