package Monad;

import java.util.function.Function;
/**
 * Autor: Jakub A. Gramsz
 * Data: 13.04.14
 *
 *  Monada stanu
 */

public class State<S, V> {
    final Function<S, Pair<V, S>> st;

    private State(Function<S, Pair<V, S>> stateTransform) {
        st = stateTransform;
    }

    public static <S, V> State<S, V> unit(V v) {
        return new State<S, V>(s0 -> new Pair<>(v, s0));
    }
    /*
    public <W> State bind(Function<V, State<S, W>> f) {
        return new State((S s0) -> {
                Pair<V, S> p1 = this.st.apply(s0);
                State<S, W> n = f.apply(p1.a);
                Pair<W, S> p2 = n.st.apply(p1.b);
                return p2;
                }
        );
    }
    public <W> State<S, W> map(Function<V, W> f) {
        return new State( (S s0) -> {
            Pair<V, S> p1 = this.st.apply(s0);
            W w = f.apply(p1.a);
            S s2 = p1.b;         // przekazywany jest stan bez zmiany
            return new Pair<>(w, s2);
        } );
    }

    public <W> State<S, W> mapVS(Function<V, W> f, Function<S, S> stransf) {
        return new State( (S s0) -> {
            Pair<V, S> p1 = this.st.apply(s0);
            W w = f.apply(p1.a);
            S s2 = stransf.apply(p1.b);
            return new Pair<>(w, s2);
        } );
    }
        */
    public void init(S initState) {

    }
}

class Pair<A, B> {
    A a;
    B b;
    public Pair(A a, B b) {
       this.a = a;
       this.b = b;
    }
}