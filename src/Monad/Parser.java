package Monad;

import Primitives.Pair;
import Monad.ListMonad;
import java.util.function.Function;

/**
 * Created by olorin on 18.06.14.
 */
public class Parser<T> {
    final Function<String, ListMonad<Pair<T, String>>> p;
    public ListMonad<Pair<T, String>> parse(String s) {
        return this.p.apply(s);
    }

    private Parser(Function<String, ListMonad<Pair<T, String>>> p) {
        this.p = p;
    }
    public static <T> Parser<T> unit(T val) {
        return new Parser<T>((String s) -> { ListMonad<Pair<T, String>> l = new ListMonad<>();
                                             l.add(new Pair<>(val, s));
                                             return l;} );
    }
    public static Parser<Character> item() {
         return new Parser<>((String s) -> {
             ListMonad<Pair<Character, String>> l = new ListMonad<>();
             l.add(new Pair<>(s.charAt(0), s.substring(1)));
             return l; } );
    }
    public <U> Parser<U> bind(Function<T, Parser<U>> k) {
        return new Parser<U>( (String x) -> (this.p.apply(x)).bind((T a) -> (k.apply(a)).p.apply(y)));
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