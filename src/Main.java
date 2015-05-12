import Monad.*;
import Primitives.*;

import java.lang.Character;
import java.lang.String;
import java.lang.System;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Created by Olórin on 12.04.14.
 */
public class Main {
    public static void main(String[] args)
    {
        testParser();
        //testMyMonads();
        //testMonadLowInOptional();
    }

    public static void testParser()
    {
        System.out.println("\n");
        System.out.println("Test monady Parser");
        System.out.println("\n");

        String inp_str = "Napis...";

        System.out.println("Parsowany łańcuch: \"" + inp_str + "\"");

        ListMonad<Pair<Character, String>> res1 = (Parser.item()).parse(inp_str);
        System.out.println("parse item we. : " + res1);

        ListMonad<Pair<Integer, String>> res2 = (Parser.unit(6)).parse("Napis2");
        System.out.println("parse (unit 6) \"Napis2\" ");

        ListMonad<Pair<String,String>> res3 = (Parser.item().bind( x ->
                                               Parser.item().bind(y ->
                                                       Parser.unit("[" + x + ", " + y + "]")))).parse(inp_str);
        System.out.println("parse spec " + res3);

        System.out.println("Parser oneOrTwoItems");
        Parser<String> oneOrTwoItems = (Parser.item().bind( x ->
                                        Parser.unit(x.toString()))
                                       )
                                  .plus(Parser.item().bind( x ->
                                        Parser.item().bind( y ->
                                        Parser.unit(x + "" + y)))
                                       );
        System.out.println("parse oneOrTwoItems \"\" == " + oneOrTwoItems.parse("").toString());
        System.out.println("parse oneOrTwoItems \"s\" == " + oneOrTwoItems.parse("s").toString());
        System.out.println("parse oneOrTwoItems \"slowo\" == " + oneOrTwoItems.parse("slowo").toString());

        System.out.println("parse letter \"Napis\" " + Prs.letter.parse("Napis"));
        System.out.println("parse letter \"1234\" " + Prs.letter.parse("1234"));

        System.out.println("parse digit \"Napis\" " + Prs.digit.parse("Napis"));
        System.out.println("parse digit \"1234\" " + Prs.digit.parse("1234"));

        System.out.println("parse lit 'N' \"Napis\" " + Prs.lit('N').parse("Napis"));

        System.out.println("parse number '3700' " + Prs.number.parse("3700"));

        System.out.println("parse term '(1/2)' " + Prs.term().parse("(1/2)"));
        System.out.println("parse term '(3/(6/2))' " + Prs.term().parse("(3/(6/2))"));
        System.out.println("parse term '1774' " + Prs.term().parse("1774"));
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
                Validation.unit(n+1).bind(k ->
                        Validation.unit(k*3)
                )
        ) );
        System.out.println( liczba2.<Integer>bind(i -> blad) );
        System.out.println(liczba2.bind(n -> (blad).map(v -> v * 10 + 1)));

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
        ListMonad<Integer> lm = ListMonad.<Integer>of(1, 2, 3);
        System.out.println("Liczba z " + lm +": " + lm.foldl((k, n) -> 10*k + n, 0) );
        System.out.println("Definiujemy dwie wartości monadyczne : " + vs + " oraz " + us );
        System.out.println("Tytaj dodatkowo foldl, suma pierwszej " + vs.foldl((a, b) -> a + b, 0) + " suma drugiej: " +
                            us.foldl((a,b) -> a + b, 0));
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
