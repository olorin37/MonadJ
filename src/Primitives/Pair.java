package Primitives;

/**
 * Created by olorin on 09.06.14.
 */
public class Pair<Fst, Snd> {
    public Fst fst;
    public Snd snd;
    public Pair(Fst fst, Snd snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public String toString() {
        return "("+fst+", "+snd+")";
    }
}
