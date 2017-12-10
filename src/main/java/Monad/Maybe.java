/**
 * Autor: Jakub A. Gramsz
 * Data: 12.04.14
 *
 *  Monada opcjonalności - Maybe
 */
package Monad;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Maybe<T> {
    protected Maybe() {}

    public static <T> Maybe<T> unit(final T val) {
        return new Just<>(val);
    }
    public static <T> Maybe<T> nothing() {
        return new Nothing<>();
    }

    public abstract  <B> Maybe<B> bind(Function<T, Maybe<B>> f);
    public abstract  <B> Maybe<B> map(Function<T, B> f);
    public abstract boolean isPresent();
    public abstract void ifPresent(Consumer<? super T> consumer);

    // dopasowanie nazewnictwa
    public <B> Maybe<B> flatMap(Function<T, Maybe<B>> k) {
        return this.bind(k);
    }
}

class Just<T> extends Maybe<T> {
    private final T value;
    protected Just(T val) {
        value = val;
    }

    public <B> Maybe<B> bind(Function<T, Maybe<B>> f) {
        return f.apply(value);
    }
    public <B> Maybe<B> map(Function<T, B> f) {
        return new Just<>( f.apply(value) );
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
        consumer.accept(value);
    }

    @Override
    public String toString() {
        return "Just " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass().equals(obj.getClass())) {
            return this.value.equals(((Just<T>) obj).value);
        } else
            return false;
    }
}

class Nothing<T> extends Maybe<T> {
    protected Nothing() {}

    public <B> Maybe<B> bind(Function<T, Maybe<B>> f) {
        return new Nothing<>();
    }
    public <B> Maybe<B> map(Function<T, B> f) {
        return new Nothing<>();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
        return;
    }

    @Override
    public String toString() {
        return "Nothing";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass().equals(obj.getClass())) {
            return !this.isPresent() && !((Maybe<T>)obj).isPresent();
        } else
            return false;
    }
}
