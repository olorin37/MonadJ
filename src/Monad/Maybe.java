/**
 * Autor: Jakub A. Gramsz
 * Data: 12.04.14
 *
 *  Monada opcjonalności - Maybe
 */
package Monad;

import java.util.function.Function;

public abstract class Maybe<T> {
    public static <T> Maybe<T> unit(final T val) {
        return new Just<>(val);
    }
    public static <T> Maybe<T> nothing() {
        return (Maybe<T>) new Nothing<>();
    }

    public abstract  <B> Maybe<B> bind(Function<T, Maybe<B>> f);
    public abstract  <B> Maybe<B> map(Function<T, B> f);
}

class Just<T> extends Maybe<T> {
    private final T value;
    public Just(T val) {
        value = val;
    }

    public <B> Maybe<B> bind(Function<T, Maybe<B>> f) {
        return f.apply(value);
    }
    public <B> Maybe<B> map(Function<T, B> f) {
        return new Just<>( f.apply(value) );
    }

    public String toString() {
        return "Just " + value;
    }
}

class Nothing<T> extends Maybe<T> {
    public Nothing() {}

    public <B> Maybe<B> bind(Function<T, Maybe<B>> f) {
        return (Maybe<B>) new Nothing<>();
    }
    public <B> Maybe<B> map(Function<T, B> f) {
        return (Maybe<B>) new Nothing<>();
    }

    public String toString() {
        return "Nothing";
    }
}
