package Primitives;

/**
 * Created by olorin on 09.06.14.
 */
public class Pair<S, V> {
    public S s;
    public V v;
    public Pair(S s, V v) {
        this.s = s;
        this.v = v;
    }

    @Override
    public String toString() {
        return "("+s+", "+v+")";
    }
}
