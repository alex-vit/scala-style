package scala_style.option;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import scala_style.None;
import scala_style.Option;
import scala_style.Some;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static scala_style.ExceptionUtils.expect;
import static scala_style.Option.*;

public class OptionTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void noneEqualsNone() {
        Option<Integer> noInteger = empty();
        Option<String> noString = empty();
        //noinspection EqualsBetweenInconvertibleTypes
        assert noInteger.equals(noString);
    }

    @Test
    public void optionsWithEqualValuesAreEqual() {
        Number number = 5;
        Integer integer = 5;

        assert number.equals(integer) && integer.equals(number);

        Option<Number> numberOption = Option(number);
        Option<Integer> integerOption = Option(integer);

        assert numberOption.equals(integerOption) && integerOption.equals(numberOption);
    }

    @Test
    public void optionsWithNonEqualValuesAreNotEqual() {
        Option<Integer> option5 = Option(5);
        Option<Integer> option7 = Option(7);

        assert !option5.equals(option7) && !option7.equals(option5);
    }

    @Test
    public void optionsOfUnrelatedTypesAreNotEqual() {
        Option<Integer> integerOption = Option(5);
        Option<String> stringOption = Option("");

        //noinspection EqualsBetweenInconvertibleTypes
        assert !integerOption.equals(stringOption) && !stringOption.equals(integerOption);
    }

    @Test
    public void someAndNoneOfSameTypeAreNotEqual() {
        Option<Integer> integerOption = Option(5);
        Option<Integer> noIntegerOption = empty();

        assert !integerOption.equals(noIntegerOption) && !noIntegerOption.equals(integerOption);
    }

    @Test
    public void optionOfNullIsNone() {
        Option<Integer> option = Option(null);
        assert option instanceof None<?>;
    }

    @Test
    public void optionOfValueIsSome() {
        Option<Integer> option = Option(5);
        assert option instanceof Some<?>;
    }

    @Test
    public void emptyReturnsNone() {
        assert empty() instanceof None<?>;
    }

    @Test
    public void whenReturnsNoneOnFalse() {
        assert when(false, () -> 5) instanceof None<?>;
    }

    @Test
    public void whenReturnsSomeOnTrue() {
        assert when(true, () -> 5) instanceof Some<?>;
    }

    @Test
    public void unlessReturnsNoneOnTrue() {
        assert unless(true, () -> 5) instanceof None<?>;
    }

    @Test
    public void unlessReturnsSomeOnFalse() {
        assert unless(false, () -> 5) instanceof Some<?>;
    }

    @Test
    public void getOrElseThrowsIllegalArgumentExceptionWhenNotSuperType() {
        expect(exception, IllegalArgumentException.class, ERROR_NOT_SUPERTYPE);

        Option(5).getOrElse(String.class, () -> "");
    }

    @Test
    public void getOrElseThrowsIllegalArgumentExceptionWhenNotSupertype() {
        Option<Integer> option = Option(5);
        Supplier<String> default_ = () -> "";

        expect(exception, IllegalArgumentException.class, ERROR_NOT_SUPERTYPE);

        option.getOrElse(String.class, default_);
    }

    @Test
    public void getOrElseReturnsCastedValueWhenSome() {
        int value = 5;
        Option<Integer> option = Option(value);
        Number orElse = option.getOrElse(Number.class, () -> 7);
        //noinspection ConstantConditions
        assert orElse instanceof Number;
        assert orElse.equals(value);
    }

    @Test
    public void getOrElseReturnsDefaultWhenNone() {
        Option<Integer> option = empty();
        Supplier<Integer> default_ = () -> 5;
        Integer orElse = option.getOrElse(Integer.class, default_);
        assert orElse.equals(default_.get());
    }

    @Test
    public void orNullReturnsValueWhenSome() {
        assert Objects.equals(Option(5).orNull(), 5);
    }

    @Test
    public void orNullReturnsNullWhenNone() {
        assert empty().orNull() == null;
    }

    @Test
    public void orElseThrowsIllegalArgumentExceptionWhenNotSupertype() {
        Option<Integer> option = Option(5);
        Supplier<Option<String>> default_ = () -> Option("");

        expect(exception, IllegalArgumentException.class, ERROR_NOT_SUPERTYPE);

        option.orElse(String.class, default_);
    }

    @Test
    public void orElseReturnsIdenticalOptionWhenSome() {
        Option<Integer> option = Option(5);
        Supplier<Option<Integer>> default_ = () -> Option(7);
        Option<Integer> newOption = option.orElse(Integer.class, default_);

        assert newOption.equals(option);
    }

    @Test
    public void orElseReturnsDefaultWhenNone() {
        Option<Integer> none = empty();
        Supplier<Option<Integer>> default_ = () -> Option(7);

        assert none.orElse(Integer.class, default_).equals(default_.get());
    }

    @Test
    public void mapReturnsNoneWhenEmpty() {
        assert empty().map(o -> 5).isEmpty();
    }

    @Test
    public void mapReturnsCorrectValueWhenNotEmpty() {
        Function<Integer, Integer> f = i -> i * 2;
        int value = 5;
        int expected = f.apply(value);

        assert Option(value).map(f).get().equals(expected);
    }

    @Test
    public void foldReturnsIfEmptyIfEmpty() {
        assert empty().fold(() -> 5, o -> o).equals(5);
    }

    @Test
    public void foldAppliesFunctionIfNotEmpty() {
        Function<Integer, Integer> f = i -> i * 2;
        int value = 5;
        int expected = f.apply(value);

        assert Option(value).fold(() -> 7, f).equals(expected);
    }

    @Test
    public void flatMapReturnsNoneWhenEmpty() {
        assert Option.<Integer>empty().flatMap(Option::Option).isEmpty();
    }

    @Test
    public void flatMapAppliesFunctionWhenNotEmpty() {
        Option<Integer> five = Option(5);
        assert five.flatMap(Option::Option).equals(five);
    }

}
