import Monad.*;
import Primitives.Pair;

import java.util.Optional;
import java.util.List;
import java.util.function.BiFunction;
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
        System.out.println("Test obliczeń monadycznych");

        System.out.println("Test Monady Maybe");

        Maybe<Integer> liczba = Maybe.unit(10);
        Maybe<Integer> nic = Maybe.<Integer>nothing();
        Maybe<Integer> jedenastka = Maybe.nothing();
        System.out.println("Definiujemy dwie wartości monadyczne : " + liczba + " oraz " + nic );
        System.out.println("Wyniki obliczeń:");
        System.out.println( liczba.bind(n -> Maybe.unit(n+1)) );
        System.out.println( nic.bind(n -> Maybe.unit(n + 4).map((m -> (m + 1) / 2 < 9))) );
        System.out.println( "Czy "+ jedenastka +" equals liczba ("+ liczba.bind(n -> Maybe.nothing()) +"): "
                + jedenastka.equals(liczba.bind(n -> Maybe.nothing())) );

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

        System.out.println("\nTest Monady listowej");
        ListMonad<Integer> vs = ListMonad.of(70,80);
        ListMonad<Integer> us = ListMonad.of(1,2,3,4,5);
        System.out.println("Definiujemy dwie wartości monadyczne : " + vs + " oraz " + us );
        System.out.println("Wyniki obliczeń:");
        System.out.println( vs.bind( x ->
                            us.bind( y ->
                            ListMonad.unit(x + y) )));

        BiFunction<Integer, Integer, Integer> inc = (a, b) -> a+1;

        System.out.println("\nTest Monady stanu");
        State<Integer, Integer> mstat = State.unit(10);
        State<Integer, Integer> res =
                mstat.transform(inc).bind(x ->
                        State.<Integer, Integer>unit(5)
                                .transform(inc).bind(y ->
                                State.unit(x + y)));
        System.out.println("Wynik obiczeń i stan " + res.evaluateFor(0));
    
        System.out.println("\nTest Moanda kontynuacyjna");
        Continuation<Integer,String> mcont1 = Continuation.<Integer,String>unit(10);
        Continuation<Integer,String> mcont2 = Continuation.<Integer,String>unit(1);
        Continuation<Integer,String> p =
                mcont1.bind( x ->
                Continuation.<Integer,String>unit(x+1).bind( y ->
                mcont2.<Integer>bind(z ->
                Continuation.<Integer,String>unit(z + y))));

        System.out.println("Wynik: " + p.evaluate(n -> n.toString()));
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
