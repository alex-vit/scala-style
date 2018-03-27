package scala_style.option;

import org.junit.jupiter.api.Test;
import scala_style.None;
import scala_style.Option;
import scala_style.Some;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static scala_style.ExceptionUtils.expect;
import static scala_style.Option.*;

class OptionTest {

    @Test
    void noneEqualsNone() {
        Option<Integer> noInteger = empty();
        Option<String> noString = empty();
        //noinspection EqualsBetweenInconvertibleTypes
        assert noInteger.equals(noString);
    }

    @Test
    void optionsWithEqualValuesAreEqual() {
        Number number = 5;
        Integer integer = 5;

        assert number.equals(integer) && integer.equals(number);

        Option<Number> numberOption = Option(number);
        Option<Integer> integerOption = Option(integer);

        assert numberOption.equals(integerOption) && integerOption.equals(numberOption);
    }

    @Test
    void optionsWithNonEqualValuesAreNotEqual() {
        Option<Integer> option5 = Option(5);
        Option<Integer> option7 = Option(7);

        assert !option5.equals(option7) && !option7.equals(option5);
    }

    @Test
    void optionsOfUnrelatedTypesAreNotEqual() {
        Option<Integer> integerOption = Option(5);
        Option<String> stringOption = Option("");

        //noinspection EqualsBetweenInconvertibleTypes
        assert !integerOption.equals(stringOption) && !stringOption.equals(integerOption);
    }

    @Test
    void someAndNoneOfSameTypeAreNotEqual() {
        Option<Integer> integerOption = Option(5);
        Option<Integer> noIntegerOption = empty();

        assert !integerOption.equals(noIntegerOption) && !noIntegerOption.equals(integerOption);
    }

    @Test
    void optionOfNullIsNone() {
        Option<Integer> option = Option(null);
        assert option instanceof None<?>;
    }

    @Test
    void optionOfValueIsSome() {
        Option<Integer> option = Option(5);
        assert option instanceof Some<?>;
    }

    @Test
    void emptyReturnsNone() {
        assert empty() instanceof None<?>;
    }

    @Test
    void whenReturnsNoneOnFalse() {
        assert when(false, () -> 5) instanceof None<?>;
    }

    @Test
    void whenReturnsSomeOnTrue() {
        assert when(true, () -> 5) instanceof Some<?>;
    }

    @Test
    void unlessReturnsNoneOnTrue() {
        assert unless(true, () -> 5) instanceof None<?>;
    }

    @Test
    void unlessReturnsSomeOnFalse() {
        assert unless(false, () -> 5) instanceof Some<?>;
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
        assert orElse instanceof Number;
        assert orElse.equals(value);
    }

    @Test
    void getOrElseReturnsDefaultWhenNone() {
        Option<Integer> option = empty();
        Supplier<Integer> default_ = () -> 5;
        Integer orElse = option.getOrElse(Integer.class, default_);
        assert orElse.equals(default_.get());
    }

    @Test
    void orNullReturnsValueWhenSome() {
        assert Objects.equals(Option(5).orNull(), 5);
    }

    @Test
    void orNullReturnsNullWhenNone() {
        assert empty().orNull() == null;
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

        assert newOption.equals(option);
    }

    @Test
    void orElseReturnsDefaultWhenNone() {
        Option<Integer> none = empty();
        Supplier<Option<Integer>> default_ = () -> Option(7);

        assert none.orElse(Integer.class, default_).equals(default_.get());
    }

    @Test
    void mapReturnsNoneWhenEmpty() {
        assert empty().map(o -> 5).isEmpty();
    }

    @Test
    void mapReturnsCorrectValueWhenNotEmpty() {
        Function<Integer, Integer> f = i -> i * 2;
        int value = 5;
        int expected = f.apply(value);

        assert Option(value).map(f).get().equals(expected);
    }

    @Test
    void foldReturnsIfEmptyIfEmpty() {
        assert empty().fold(() -> 5, o -> o).equals(5);
    }

    @Test
    void foldAppliesFunctionIfNotEmpty() {
        Function<Integer, Integer> f = i -> i * 2;
        int value = 5;
        int expected = f.apply(value);

        assert Option(value).fold(() -> 7, f).equals(expected);
    }

    @Test
    void flatMapReturnsNoneWhenEmpty() {
        assert Option.<Integer>empty().flatMap(Option::Option).isEmpty();
    }

    @Test
    void flatMapAppliesFunctionWhenNotEmpty() {
        Option<Integer> five = Option(5);
        assert five.flatMap(Option::Option).equals(five);
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
