package Monad;

import java.util.function.Function;

/**
 * Created by olorin on 08.06.14.
 */
public interface Bindable<T> {

    <U> Bindable<U> bind(Function<T, Bindable<U>> k);     // Function<? super T, Bindable<U>>
    <U> Bindable<U> map(Function<T, U> f);  //  Function<? super T, ? extends U>

}
