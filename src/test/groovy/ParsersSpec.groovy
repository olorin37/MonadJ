import spock.lang.*

import Monad.ListMonad
import Primitives.Pair


class ParsersSpec extends Specifiaction {
    def 'simple div term'() {
        expect:
        Prs.term().parse(text).get(0).fst == ast
        
        where:
        text       || ast
        '(1/2)'    || new Div<>(new Con<>(1), new Con<>(2))
    }
}