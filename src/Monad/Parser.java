package Monad;

import Primitives.Pair;
import Monad.ListMonad;
import java.util.function.Function;

/**
 * Created by olorin on 18.06.14.
 */
public class Parser<T> {
    final Function<String, ListMonad<Pair<String, T>>> p;

    private Parser(Function<String, ListMonad<Pair<String, T>>> p) {
        this.p = p;
    }
    public static <T> Parser<T> unit(T val) {
        return new Parser<T>((String s) -> { ListMonad<Pair<String, T>> l = new ListMonad<>();
                                             l.add(new Pair<>(s, val));
                                             return l;} );
    }
    public Parser<Character> item() {
         return new Parser<>((String s) -> {
             ListMonad<Pair<String, Character>> l = new ListMonad<>();
             l.add(new Pair<>(s.substring(1), s.charAt(0)));
             return l; } );
    }
    public <U> Parser<U> bind(Function<T, Parser<U>> k) {
        String x = "";
        return (this.p(x)).bind((T a) -> new Parser<U>(y -> (k(a)).p(y)));
    }
}


abstract class Term<T> {
}

class Div<T> extends Term<T> {
    final Term<T> arg1;
    final Term<T> arg2;
    Div(Term<T> arg1, Term<T> arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
}
class Con<T> extends Term<T> {
    final T val;
    Con(T value) {
        val = value;
    }
}