package Monad;

import java.util.function.BiFunction;
import java.util.function.Function;
import Primitives.Pair;
/**
 * Autor: Jakub A. Gramsz
 * Data: 13.04.14
 *
 *  Monada stanu
 */

public class State<S, V> {
    final Function<S, Pair<S, V>> st;

    private State(Function<S, Pair<S, V>> stateTransform) {
        st = stateTransform;
    }

    public static <S, V> State<S, V> unit(V v) {
        return new State<>(s0 -> new Pair<>(s0, v));
    }

    public <W> State<S, W> bind(Function<V, State<S, W>> f) {
        return (new State<S, W>( (S s0) ->
            {
                Pair<S, V> p1 = this.st.apply(s0);
                State<S, W> m = f.apply(p1.snd);
                Pair<S, W> p2 = m.st.apply(p1.fst);
                return (Pair<S, W>) p2;
            }
        ));
    }

    public <W> State<S, W> map(Function<V, W> f) {
        return new State<S, W>( (S s0) -> {
            Pair<S, V> p1 = this.st.apply(s0);
            W w = f.apply(p1.snd);
            return new Pair<>(p1.fst, w);
        } );
    }

    public State<S, V> transform(BiFunction<S, V, S> stransform ) {
        return (new State<S, V>( (S s0) ->
        {
            Pair<S, V> p1 = this.st.apply(s0);
            S s = stransform.apply(p1.fst, p1.snd);
            return new Pair<S, V>(s, p1.snd);
        }
        ));
    }

    public Pair<S, V> evaluateFor(S initialState) {
        Pair<S, V> p = st.apply(initialState);
         return p;
    }
}