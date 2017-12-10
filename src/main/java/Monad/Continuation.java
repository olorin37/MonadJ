/**
 * Autor: Jakub A. Gramsz
 * Data: 18.04.14
 *
 *  Monada kontynuacji
 */
package Monad;

import java.util.function.Function;

public class Continuation<V, Ans> {
    final Function<Function<V, Ans>, Ans> cont;
    public Continuation(V v) {
        cont = c -> c.apply(v);
    }
    private Continuation(Function<Function<V, Ans>, Ans> cont) {
        this.cont = cont;
    }

    public static <V, Ans> Continuation<V, Ans> unit(V v) {
        return new Continuation<V, Ans>(c -> c.apply(v));
    }

    public <W> Continuation<W, Ans> bind(Function<V, Continuation<W, Ans>> f) {       // coś tu nie gra, i w State
        return new Continuation<W, Ans>(c -> cont.apply(a -> f.apply(a).cont.apply(c)));
    }
    public <W> Continuation<W, Ans> map(Function<V, W> f) {
        return this.bind(v -> unit(f.apply(v)));
        //return new Continuation<W, Ans>(c -> cont.apply(a -> f.apply(a)));
    }

    public Ans evaluate(Function<V, Ans> outf) {
        return cont.apply(outf);
    }

    // dopasowanie nazewnictwa
    public <B> Continuation<B, Ans> flatMap(Function<V, Continuation<B, Ans>> k) {
        return this.bind(k);
    }
}
