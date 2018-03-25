package scala_style.util;

import static scala_style.util.Generics.isSuperType;
import org.junit.jupiter.api.Test;

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
