/**
 * Autor: Jakub A. Gramsz
 * Data: 12.04.14
 *
 *  Monada walidacyna - Error
 */
package Monad;

import java.util.function.Function;

public abstract class Validation<T> {
    protected Validation() {}

    public static <T> Validation<T> unit(T val) {
        return new Success(val);
    }
    public static <T> Validation<T> error(String errorMessage) {
        return new Error<T>(errorMessage);
    }

    public abstract  <B> Validation<B> bind(Function<T, Validation<B>> f);
    public abstract  <B> Validation<B> map(Function<T, B> f);
}

class Success<T> extends Validation<T> {
    private final T value;
    protected Success(T val) {
        value = val;
    }

    public <B> Validation<B> bind(Function<T, Validation<B>> f) {
        return f.apply(value);
    }
    public <B> Validation<B> map(Function<T, B> f) {
        return new Success<B>( f.apply(value) );
    }

    public String toString() {
        return "Success " + value;
    }
}

class Error<T> extends Validation<T> {
    private final String errorMsg;
    protected Error(String msg) {
        errorMsg = msg;
    }

    public <B> Validation<B> bind(Function<T, Validation<B>> f) {
        return new Error<B>(errorMsg);
    }
    public <B> Validation<B> map(Function<T, B> f) {
        return new Error<B>(errorMsg);
    }

    public String toString() {
        return "Error \"" + errorMsg +"\"";
    }
}