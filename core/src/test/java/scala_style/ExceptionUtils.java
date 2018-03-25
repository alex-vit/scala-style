package scala_style;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.rules.ExpectedException;

public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static <T extends Throwable> void expect(ExpectedException exception, Class<T> type, String message) {
        exception.expect(type);
        exception.expectMessage(message);
    }

    public static <T extends Throwable, U extends Throwable> void expect(
            ExpectedException exception, Class<T> type, String message,
            Class<U> causeType
    ) {
        expect(exception, type, message, causeType, null);
    }

    public static <T extends Throwable, U extends Throwable> void expect(
            ExpectedException exception, Class<T> type, String message,
            Class<U> causeType, String causeMessage
    ) {
        expect(exception, type, message);
        exception.expectCause(matcher(causeType, causeMessage));
    }

    private static <T extends Throwable> BaseMatcher<T> matcher(Class<T> type) {
        return matcher(type, null);
    }

    private static <T extends Throwable> BaseMatcher<T> matcher(Class<T> type, String message) {
        return new BaseMatcher<T>() {
            @Override
            public boolean matches(Object item) {
                boolean classMatches = item.getClass().equals(type);

                if (message != null) {
                    T exception = type.cast(item);
                    boolean messageMatches = exception.getMessage().matches(message);
                    return classMatches && messageMatches;
                } else {
                    return classMatches;
                }
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

}
