package scala_style.util;

import scala_style.Option;
import scala_style.function.Function;
import scala_style.function.Supplier;

import static scala_style.util.Failure.Failure;
import static scala_style.util.Success.Success;

public abstract class Try<A> {

    public static final String ERROR_FAILURE_OF_NULL = "Failure(null)";
    public static final String ERROR_SUCCESS_OF_NULL = "Success(null)";
    public static final String ERROR_FAILURE_DOT_GET = "Failure.get";
    public static final String ERROR_SUCCESS_DOT_FAILED = "Success.failed";

    @SuppressWarnings("MethodNameSameAsClassName")
    public static <A> Try<A> Try(Supplier<A> r) {
        try {
            return Success(r.get());
        } catch (Throwable e) {
            return Failure(e);
        }
    }

    public abstract boolean isFailure();

    public abstract boolean isSuccess();

    public abstract A get();

    public abstract Try<Throwable> failed();

    public abstract <B> B getOrElse(Class<B> type, Supplier<B> default_);

    public abstract <B> Try<B> orElse(Class<B> type, Supplier<Try<B>> default_);

    public abstract <B> Try<B> map(Function<A, B> f);

    public abstract <B> Try<B> flatMap(Function<A, Try<B>> f);

    public abstract <B> B fold(Function<Throwable, B> fa, Function<A, B> fb);

    public abstract <B> Try<B> recoverWith(Class<B> type, Function<Throwable, Try<B>> f);

    public abstract <B> Try<B> recover(Class<B> type, Function<Throwable, B> f);

    public abstract Option<A> toOption();

    public abstract Either<Throwable, A> toEither();

}
