package scala_style;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static <T extends Throwable, U extends Throwable> void expect(
            Executable run, Class<T> type
    ) {
        expect(run, type, null);
    }

    public static <T extends Throwable, U extends Throwable> void expect(
            Executable run, Class<T> type, String message
    ) {
        expect(run, type, message, null);
    }

    public static <T extends Throwable, U extends Throwable> void expect(
            Executable run, Class<T> type, String message,
            Class<U> causeType
    ) {
        expect(run, type, message, causeType, null);
    }

    public static <T extends Throwable, U extends Throwable> void expect(
            Executable run, Class<T> type, String message,
            Class<U> causeType, String causeMessage
    ) {
        T t = assertThrows(type, run);
        assertEquals(t.getMessage(), message);
        if (causeType != null) assertEquals(causeType, t.getCause().getClass());
        if (causeMessage != null) assertEquals(causeMessage, t.getCause().getMessage());
    }

}
