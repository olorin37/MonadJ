package Primitives;

public class Div<T> extends Term<T> {
    final Term<T> arg1;
    final Term<T> arg2;
    public Div(Term<T> arg1, Term<T> arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
    public String toString() {
        return "(Div " + arg1.toString() + " " + arg2.toString()  + ")";
    }
}