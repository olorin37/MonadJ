package Monad;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by olorin on 07.06.14.
 */
public class MonadLows {
    public static boolean low1_Optional(Function<Integer, Optional<Integer>> k,
                               Integer v)
    {
        Optional<Integer> left  = Optional.of(1).flatMap( k );
        Optional<Integer> right = k.apply(v);
        System.out.print(left + " == " + right + " => ");
        return left.equals(right);
    }
    public static boolean low2_Optional(Optional<Integer> m)
    {
        Optional<Integer> left  = m.flatMap(Optional::of);
        Optional<Integer> right = m;
        System.out.print(left + " == " + right + " => ");
        return left.equals(right);
    }
    public static boolean low3_Optional(Optional<Integer> m,
                               Function<Integer, Optional<Integer>> k,
                               Function<Integer, Optional<Integer>> h)
    {
        Optional<Integer> left  =  (m.flatMap(k)).flatMap(h);
        Optional<Integer> right =  m.flatMap(v -> k.apply(v).flatMap(h));
        System.out.print(left + " == " + right + " => ");
        return left.equals(right);
    }
}
