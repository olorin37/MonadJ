/**
 * Autor: Jakub A. Gramsz
 * Data: 07.06.14
 *
 *  List monad
 */
package Monad;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by olorin on 07.06.14.
 */
public class ListMonad<A> extends LinkedList<A> implements List<A> {
    public ListMonad() {
        super();
    }
    public ListMonad(A val)
    {
        super(Arrays.asList(val));
    }
    public ListMonad(Collection<A> c) {
        super(c);
    }
    public ListMonad(A head, ListMonad<A> tail) {
        
    }

    public static <T> ListMonad<T> unit(T val) {
        return new ListMonad<T>(val);
    }
    public static <T> ListMonad<T> of(T... values) {
        ListMonad<T> res = new ListMonad<T>();
        for (T val : values) {
            res.add(val);
        }
        return res;
    }
    public <B> ListMonad<B> bind(Function<A, ListMonad<B>> k) {
        return ListMonad.concat(this.map(k));
    }

    // dostosowanie nazewnictwa
    public <B> ListMonad<B> flatMap(Function<A, ListMonad<B>> k) {
        return this.bind(k);
    }

    // pomocnicze funkcje

    public <B> ListMonad<B> map(Function<A, B> f) {
        ListMonad<B> res = new ListMonad<B>();
        this.forEach(x -> res.add( f.apply(x) ));
        return res;
    }
    public static <T> ListMonad<T> concat(ListMonad<ListMonad<T>> tlistlist) {
        ListMonad<T> res = new ListMonad<T>();
        tlistlist.forEach(tlist -> res.addAll(tlist));
        return res;
    }

    public ListMonad<A> concatWith(ListMonad<A> snd) {
        ListMonad<A> res = new ListMonad<A>();
        res.addAll(this);
        res.addAll(snd);
        return res;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Monadic[");
        this.forEach(v -> sb.append(v + ", "));
        return sb.substring(0, (this.size() > 0) ? sb.length()-2 : sb.length()) + "]";
    }
}
