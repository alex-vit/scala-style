package scala_style.option;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import scala_style.None;
import scala_style.Option;
import scala_style.Some;
import scala_style.function.Function;
import scala_style.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static scala_style.ExceptionUtils.expect;
import static scala_style.Option.ERROR_EMPTY_ITERATOR_GET;
import static scala_style.Option.ERROR_NOT_SUPERTYPE;
import static scala_style.Option.Option;
import static scala_style.Option.empty;
import static scala_style.Option.unless;
import static scala_style.Option.when;

class OptionTest {

    @Test
    void noneEqualsNone() {
        Option<Integer> noInteger = empty();
        Option<String> noString = empty();
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(noInteger, noString);
    }

    @Test
    void optionsWithEqualValuesAreEqual() {
        Number number = 5;
        Integer integer = 5;

        assertEquals(number, integer);
        assertEquals(integer, number);

        Option<Number> numberOption = Option(number);
        Option<Integer> integerOption = Option(integer);

        assertEquals(numberOption, integerOption);
        assertEquals(integerOption, numberOption);
    }

    @Test
    void optionsWithNonEqualValuesAreNotEqual() {
        Option<Integer> option5 = Option(5);
        Option<Integer> option7 = Option(7);

        assertNotEquals(option5, option7);
        assertNotEquals(option7, option5);
    }

    @Test
    void optionsOfUnrelatedTypesAreNotEqual() {
        Option<Integer> integerOption = Option(5);
        Option<String> stringOption = Option("");

        assertNotEquals(integerOption, stringOption);
        assertNotEquals(stringOption, integerOption);
    }

    @Test
    void someAndNoneOfSameTypeAreNotEqual() {
        Option<Integer> integerOption = Option(5);
        Option<Integer> noIntegerOption = empty();

        assertNotEquals(integerOption, noIntegerOption);
        assertNotEquals(noIntegerOption, integerOption);
    }

    @Test
    void optionOfNullIsNone() {
        Option<Integer> option = Option(null);
        assertTrue(option instanceof None<?>);
    }

    @Test
    void optionOfValueIsSome() {
        Option<Integer> option = Option(5);
        assertTrue(option instanceof Some<?>);
    }

    @Test
    void emptyReturnsNone() {
        assertTrue(empty() instanceof None<?>);
    }

    @Test
    void whenReturnsNoneOnFalse() {
        assertTrue(when(false, () -> 5) instanceof None<?>);
    }

    @Test
    void whenReturnsSomeOnTrue() {
        assertTrue(when(true, () -> 5) instanceof Some<?>);
    }

    @Test
    void unlessReturnsNoneOnTrue() {
        assertTrue(unless(true, () -> 5) instanceof None<?>);
    }

    @Test
    void unlessReturnsSomeOnFalse() {
        assertTrue(unless(false, () -> 5) instanceof Some<?>);
    }

    @Test
    void simpleGetOrElseReturnsValueWhenSome() {
        Option<Integer> option = Option(5);
        assertEquals(option.get(), option.getOrElse(() -> 7));
    }

    @Test
    void simpleGetOrElseReturnsDefaultWhenNone() {
        Option<Integer> option = empty();
        Supplier<Integer> default_ = () -> 5;
        assertEquals(default_.get(), option.getOrElse(default_));
    }

    @Test
    void getOrElseThrowsIllegalArgumentExceptionWhenNotSuperType() {
        expect(() -> Option(5).getOrElse(String.class, () -> ""),
                IllegalArgumentException.class,
                ERROR_NOT_SUPERTYPE);
    }

    @Test
    void getOrElseThrowsIllegalArgumentExceptionWhenNotSupertype() {
        Option<Integer> option = Option(5);
        Supplier<String> default_ = () -> "";

        expect(() -> option.getOrElse(String.class, default_),
                IllegalArgumentException.class,
                ERROR_NOT_SUPERTYPE);
    }

    @Test
    void getOrElseReturnsCastedValueWhenSome() {
        int value = 5;
        Option<Integer> option = Option(value);
        Number orElse = option.getOrElse(Number.class, () -> 7);
        //noinspection ConstantConditions
        assertTrue(orElse instanceof Number);
        assertEquals(orElse, value);
    }

    @Test
    void getOrElseReturnsDefaultWhenNone() {
        Option<Integer> option = empty();
        Supplier<Integer> default_ = () -> 5;
        Integer orElse = option.getOrElse(Integer.class, default_);
        assertEquals(orElse, default_.get());
    }

    @Test
    void orNullReturnsValueWhenSome() {
        //noinspection ConstantConditions
        assertEquals((int) Option(5).orNull(), 5);
    }

    @Test
    void orNullReturnsNullWhenNone() {
        assertNull(empty().orNull());
    }

    @Test
    void simpleOrElseReturnsThisWhenSome() {
        Option<Integer> option = Option(5);
        assertSame(option, option.orElse(() -> Option(7)));
    }

    @Test
    void simpleOrElseReturnsDefaultWhenNone() {
        Option<Integer> option = empty();
        assertNotEquals(option, option.orElse(() -> Option(7)));
    }

    @Test
    void orElseThrowsIllegalArgumentExceptionWhenNotSupertype() {
        Option<Integer> option = Option(5);
        Supplier<Option<String>> default_ = () -> Option("");

        expect(() -> option.orElse(String.class, default_),
                IllegalArgumentException.class,
                ERROR_NOT_SUPERTYPE);
    }

    @Test
    void orElseReturnsIdenticalOptionWhenSome() {
        Option<Integer> option = Option(5);
        Supplier<Option<Integer>> default_ = () -> Option(7);
        Option<Integer> newOption = option.orElse(Integer.class, default_);

        assertEquals(newOption, option);
    }

    @Test
    void orElseReturnsDefaultWhenNone() {
        Option<Integer> none = empty();
        Supplier<Option<Integer>> default_ = () -> Option(7);

        assertEquals(none.orElse(Integer.class, default_), default_.get());
    }

    @Test
    void mapReturnsNoneWhenEmpty() {
        assertTrue(empty().map(o -> 5).isEmpty());
    }

    @Test
    void mapReturnsCorrectValueWhenNotEmpty() {
        Function<Integer, Integer> f = i -> i * 2;
        int value = 5;
        Integer expected = f.apply(value);

        assertEquals(Option(value).map(f).get(), expected);
    }

    @Test
    void foldReturnsIfEmptyIfEmpty() {
        Option<Integer> option = empty();
        Integer fold = option.fold(() -> 5, o -> o);
        assertEquals((int) fold, 5);
    }

    @Test
    void foldAppliesFunctionIfNotEmpty() {
        Function<Integer, Integer> f = i -> i * 2;
        Supplier<Integer> s = () -> 7;
        Integer value = 5;
        Integer expected = f.apply(value);
        Integer fold = Option(value).fold(s, f);

        assertEquals(fold, expected);
    }

    @Test
    void flatMapReturnsNoneWhenEmpty() {
        assertTrue(Option.<Integer>empty().flatMap(Option::Option).isEmpty());
    }

    @Test
    void flatMapAppliesFunctionWhenNotEmpty() {
        Option<Integer> five = Option(5);
        assertEquals(five.flatMap(Option::Option), five);
    }

    @Test
    void noneIteratorIsEmpty() {
        Iterator<Object> iterator = Option(null).iterator();
        assertFalse(iterator.hasNext());
        expect(iterator::next, NoSuchElementException.class, ERROR_EMPTY_ITERATOR_GET);
    }

    @Test
    void someIteratorHasOneItem() {
        Iterator<Integer> iterator = Option(5).iterator();

        assertTrue(iterator.hasNext());
        int next = iterator.next();
        assertEquals(next, 5);

        assertFalse(iterator.hasNext());
        expect(iterator::next, NoSuchElementException.class, ERROR_EMPTY_ITERATOR_GET);
    }

}
