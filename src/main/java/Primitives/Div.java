package Primitives;

public class Div<T> extends Term<T> {
    final Term<T> arg1;
    final Term<T> arg2;
    public Div(Term<T> arg1, Term<T> arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Div)) return false;

        Div<?> div = (Div<?>) o;

        if (arg1 != null ? !arg1.equals(div.arg1) : div.arg1 != null) return false;
        return !(arg2 != null ? !arg2.equals(div.arg2) : div.arg2 != null);
    }

    @Override
    public int hashCode() {
        int result = arg1 != null ? arg1.hashCode() : 0;
        result = 31 * result + (arg2 != null ? arg2.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "(Div " + arg1.toString() + " " + arg2.toString()  + ")";
    }
}