import Monad.*;

import java.util.Optional;
import java.util.List;
import java.util.function.Function;

/**
 * Created by olorin on 12.04.14.
 */
public class Main {
    public static void main(String[] args)
    {
        testMyMonads();
        testMonadLowInOptional();
    }

    public static void testMyMonads()
    {
        System.out.println("Test obliczeń monadycznych Monad.*");

        System.out.println("Test Monady Maybe");

        Maybe<Integer> liczba = Maybe.unit(10);
        Maybe<Integer> nic = Maybe.<Integer>nothing();
        System.out.println("Definiujemy dwie wartości monadyczne : " + liczba + " oraz " + nic );
        System.out.println("Wyniki obliczeń:");
        System.out.println( liczba.bind(n -> Maybe.unit(n+1)) );
        System.out.println( nic.bind(n -> Maybe.unit(n + 4).map((m -> (m + 1) / 2 < 9))) );

        System.out.println("\nTest Monady Error" );

        Validation<Integer> liczba2 = Validation.unit(10);
        Validation<Integer> blad = Validation.<Integer>error("Głupi błąd w trakcie obliczeń.");
        System.out.println("Definiujemy dwie wartości monadyczne : " + liczba2 + " oraz " + blad );
        System.out.println("Wyniki obliczeń:");
        System.out.println( liczba2.bind(n ->
                Validation.<Integer>unit(n+1).bind(k ->
                        Validation.unit(k*3)
                )
        ) );
        System.out.println( liczba2.<Integer>bind(i -> blad) );
        System.out.println( liczba2.bind(n -> (blad).map(v -> v * 10 + 1)) );

        System.out.println("\nTest Monady Identity");
        Identity<Integer> v = Identity.unit(5);
        Identity<Integer> u = Identity.unit(7);
        System.out.println("Definiujemy dwie wartości monadyczne : " + v + " oraz " + u );
        System.out.println("Wyniki obliczeń:");
        System.out.println( v.bind(x ->
                            u.bind(y ->
                            Identity.unit(x + y))) );

    }

    public static void testMonadLowInOptional()
    {
        System.out.println("\nTest if Optional<> satisfies monadic lows");
        System.out.println(MonadLows.low1_Optional(v -> Optional.of(1 + v), 1));
        System.out.println(MonadLows.low2_Optional(Optional.of(1)));
        System.out.println(MonadLows.low3_Optional(Optional.of(1),
                v -> Optional.of(v + 1),
                v -> Optional.of(v + 10)) );
    }





}
