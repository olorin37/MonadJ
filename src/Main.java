import Monad.Maybe;
import Monad.Validation;
import Monad.State;

import java.util.Optional;
import java.util.List;
import java.util.function.Function;

/**
 * Created by olorin on 12.04.14.
 */
public class Main {
    public static void main(String[] args)
    {
        System.out.println(low1(v -> Optional.of(1+v), 1));
        System.out.println(low2(Optional.of(1)));
        System.out.println(low3(Optional.of(1),
                                v -> Optional.of(v+1),
                                v -> Optional.of(v+10)) );
        //System.in.read();
    }

    public static boolean low1(Function<Integer, Optional<Integer>> k,
                               Integer v)
    {
        Optional<Integer> left  = Optional.of(1).flatMap( k );
        Optional<Integer> right = k.apply(v);
        System.out.print(left + " == " + right + " => ");
        return left.equals(right);
    }
    public static boolean low2(Optional<Integer> m)
    {
        Optional<Integer> left  = m.flatMap(Optional::of);
        Optional<Integer> right = m;
        System.out.print(left + " == " + right + " => ");
        return left.equals(right);
    }
    public static boolean low3(Optional<Integer> m,
                               Function<Integer, Optional<Integer>> k,
                               Function<Integer, Optional<Integer>> h)
    {
        Optional<Integer> left  =  (m.flatMap(k)).flatMap(h);
        Optional<Integer> right =  m.flatMap(v -> k.apply(v).flatMap(h));
        System.out.print(left + " == " + right + " => ");
        return left.equals(right);
    }

    public static void main2(String[] args)
    {

        System.out.println("Witam");
        Maybe<Integer> liczba = Maybe.unit(10);
        Maybe<Integer> nic = Maybe.<Integer>nothing();
        System.out.println("Monad Maybe : opcjonalość");
        System.out.println("Deklarujemy liczbę: " + liczba );
        System.out.println( liczba.bind(n -> Maybe.unit(n+1)) );
        System.out.println( nic.bind(n -> Maybe.unit(n + 4).map((m -> (m + 1) / 2 < 9))) );

        System.out.println("\nMonad Error: walidacja" );

        Validation<Integer> liczba2 = Validation.unit(10);
        Validation<Integer> blad = Validation.<Integer>error("Głupi błąd w trakcie obliczeń.");

        System.out.println( liczba2 );
        System.out.println( liczba2.bind(n ->
                                Validation.<Integer>unit(n+1).bind(k ->
                                        Validation.unit(k*3)
                                )
        ) );
        System.out.println( liczba2.<Integer>bind(i -> blad) );
        System.out.println( liczba2.bind(n -> (blad).map(v -> v * 10 + 1)) );

        System.out.println("Stan");
        State<Integer, Integer> valst = State.unit(5);

        Optional<Integer> sth = Optional.of(37);
        Optional<Double> sth1 = sth.flatMap(i ->  Optional.of(Math.exp(1) * i));


        System.out.println(sth.toString() +"  "+ sth1.toString());
    }

}
