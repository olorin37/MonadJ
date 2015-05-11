package Primitives;

public class Con<T> extends Term<T> {
    final T val;
    public Con(T value) {
        val = value;
    }
    public String toString() {
        return "(Con " + val.toString() + ")";
    }
}