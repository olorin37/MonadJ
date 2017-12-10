package Monad;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by olorin on 08.06.14.
 */
public interface Foldable<T> {
    Bindable<T> filter(Predicate<? super T> p);
    void perform(Consumer<? super T> consumer);
    boolean isNotEmpty();
}
