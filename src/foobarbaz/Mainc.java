package foobarbaz;

import java.util.Optional;

/**
 * Created by olorin on 24.05.14.
 */
class Foo {
    Optional<Bar> bar;
}
class Bar {
    Optional<Baz> baz;
}
class Baz {
    Integer compute() {
        return 10;
    }
}
public class Mainc {
   /* void test() {
        Optional<Foo> mfoo1 = Optional.empty();
        Optional<Foo> mfoo2 = Optional.ofNullable(null);
        Optional<Foo> mfoo3 = Optional.of(new Foo().bar = Optional.of(new Bar().baz = Optional.of(new Baz())));
    }*/

    Optional<Integer> comupte(Optional<Foo> mfoo) {
        return mfoo.flatMap( foo ->
                foo.bar.flatMap( bar ->
                        bar.baz.map( baz ->
                                baz.compute() )));
    }
}
