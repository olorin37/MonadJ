import Monad.Maybe;
import Monad.Validation;
import Monad.State;

import java.util.Optional;
import java.util.List;

/**
 * Created by olorin on 12.04.14.
 */
public class Main {
    public static void main(String[] args)
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
