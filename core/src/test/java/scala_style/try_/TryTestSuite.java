package scala_style.try_;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FailureTest.class,
        SuccessTest.class,
})
public class TryTestSuite {
}
