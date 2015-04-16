package Monad;

import Primitives.Pair;
import Monad.ListMonad;

import java.lang.Boolean;
import java.lang.String;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by olorin on 18.06.14.
 */
public class Parser<T> {
    private final Function<String, ListMonad<Pair<T, String>>> p;
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
    public <U> Parser<U> bind(Function<T, Parser<U>> k) {
        return new Parser<U>( (String x) -> (this.p.apply(x)).bind((Pair<T, String> pay) -> {
            T a = pay.s;
            String y = pay.v;
            return (k.apply(a)).p.apply(y); } ));
    }

    public static <T> Parser<T> zero() {
        return new Parser<T>((String s) -> { ListMonad<Pair<T, String>> l = new ListMonad<>();
                                             return l;});
    }
    public Parser<T> plus(Parser<T> m) {
        return new Parser<T>((String s) ->((this.parse(s)).concatWith(m.parse(s))) );
    }

    public static Parser<Character> item() {
         return new Parser<>((String s) -> {
             ListMonad<Pair<Character, String>> l = new ListMonad<>();
             if (s.length() != 0) {
                 l.add(new Pair<>(s.charAt(0), s.substring(1)));
             }
             return l; } );
    }

    public Parser<T> filter(Function<T, Boolean> predicate) {
        return this.bind((T a) -> (predicate.apply(a)) ? Parser.unit(a) : Parser.zero());
    }

    public Parser<T> ifNot(Parser<T> m) {
        return new Parser<T>( (String s) -> this.parse(s).isEmpty() ? this.parse(s) : m.parse(s) );
    }

    public  Parser<ListMonad<T>> iterate() {
        return this.bind( (T a) ->
               this.iterate().bind( (ListMonad<T> x) ->
               Parser.unit(ListMonad.<T>cons(a, x))) ).ifNot(
                Parser.unit(ListMonad.<T>empty()));
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
