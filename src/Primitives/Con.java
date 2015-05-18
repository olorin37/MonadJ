package Primitives;

public class Con<T> extends Term<T> {
    public final T val;
    public Con(T value) {
        val = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Con)) return false;

        Con<?> con = (Con<?>) o;

        return !(val != null ? !val.equals(con.val) : con.val != null);
    }

    @Override
    public int hashCode() {
        return val != null ? val.hashCode() : 0;
    }

    public String toString() {
        return "(Con " + val.toString() + ")";
    }
}