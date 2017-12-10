package Monad;

import Primitives.Pair;
import Monad.ListMonad;
import java.lang.Boolean;
import java.lang.String;
import java.util.function.Function;

/**
 * Created by Olórin on 18.06.14.
 *
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
        return new Parser<>((String s) -> { ListMonad<Pair<T, String>> l = new ListMonad<>();
                                             l.add(new Pair<>(val, s));
                                             return l;} );
    }
    public <U> Parser<U> bind(Function<T, Parser<U>> k) {
        return new Parser<>( (String x) -> (this.p.apply(x)).bind((Pair<T, String> pay) -> {
            T a = pay.fst;
            String y = pay.snd;
            return (k.apply(a)).p.apply(y); } ));
    }

    public static <T> Parser<T> zero() {
        return new Parser<>((String s) ->  new ListMonad<>() );
    }
    public Parser<T> plus(Parser<T> m) {
        return new Parser<>((String s) ->((this.parse(s)).concatWith(m.parse(s))) );
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
        return new Parser<>( (String s) -> { ListMonad<Pair<T, String>> ans = this.parse(s);
                                              return !ans.isEmpty() ? ans : m.parse(s); } );
    }

    public Parser<ListMonad<T>> iterate() {
        return this                                   .bind( ( a) ->
               this.iterate()                         .bind( ( x) ->
               Parser.unit(ListMonad.cons(a, x))) )
                .ifNot(Parser.unit(ListMonad.empty()));
    }
}
