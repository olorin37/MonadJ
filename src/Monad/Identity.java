/**
 * Autor: Jakub A. Gramsz
 * Data: 12.04.14
 *
 *  Monada idientyczności - Maybe
 */
package Monad;

import java.util.function.Function;

/**
 * Created by olorin on 12.04.14.
 */
public class Identity<T> {
    protected final T value;
    public Identity(T val) {
        value = val;
    }

    public static <T> Identity unit(T val) {
        return new Identity<T>(val);
    }

    public <B> Identity<B> bind(Function<? super T, Identity<B>> f) {
        return f.apply(value);
    }

    public <B> Identity<B> map(Function<T, B> f) {
        return new Identity<B>( f.apply(value) );
    }

    public String toString() {
        return "Id " + value;
    }

    // dopasowanie nazewnictwa
    public <B> Identity<B> flatMap(Function<T, Identity<B>> k) {
        return this.bind(k);
    }
}
