package scala_style.util;

import org.junit.Test;

import static scala_style.util.Generics.isSuperType;

public class GenericsTest {

    @Test
    public void numberAndIntegerShouldReturnTrue() {
        assert isSuperType(Number.class, Integer.class);
    }

    @Test
    public void stringAndIntegerShouldReturnFalse() {
        assert !isSuperType(String.class, Integer.class);
    }

}
