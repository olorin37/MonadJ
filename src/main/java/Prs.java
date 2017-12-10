import Monad.ListMonad;
import Monad.Parser;
import Primitives.Con;
import Primitives.Div;
import Primitives.Term;

/**
 * Created by Olórin on 11.05.15.
 */
public class Prs {
    public static Parser<Character> letter = Parser.item()
            .filter((Character c) -> (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));

    public static Parser<Integer> digit = Parser.item()
                                                .filter((Character c) -> (c >= '0' && c <= '9'))
                                                .bind((Character c) ->
                                                        Parser.unit(Integer.parseInt(c.toString())));

    public static Parser<Character> lit(Character l) {
        return Parser.item().filter(l::equals);
    }

    public static Parser<Integer> number = (
            digit          .bind(a ->
            digit.iterate().bind(x ->
            Parser.unit(ListMonad.cons(a, x).foldl((n, k) -> 10 * n + k, 0))
                    )));

    public static Parser<Term<Integer>> term() {
        return ( number   .bind(a ->
                 Parser.<Term<Integer>>unit(new Con<Integer>(a)))
                 .plus(
                 lit('(') .bind(omit1 ->
                 term()   .bind(t ->
                 lit('/') .bind(omit2 ->
                 term()   .bind(u ->
                 lit(')') .bind(omit3 ->
                 Parser.<Term<Integer>>unit(new Div<Integer>(t, u))))))))
                );
    }
}
