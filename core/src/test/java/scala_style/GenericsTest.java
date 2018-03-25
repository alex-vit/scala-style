package scala_style;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static scala_style.util.Generics.isSuperType;

@RunWith(JUnitPlatform.class)
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
