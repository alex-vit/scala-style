package scala_style;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import scala_style.either.EitherTestSuite;
import scala_style.option.OptionTestSuite;
import scala_style.try_.TryTestSuite;
import scala_style.util.GenericsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EitherTestSuite.class,
        OptionTestSuite.class,
        TryTestSuite.class,
        GenericsTest.class,
})
public class TestSuite {
}
