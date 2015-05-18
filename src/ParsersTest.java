import Monad.ListMonad;
import Primitives.Pair;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import Primitives.Div;
import Primitives.Con;

/**
 * Created by Olórin on 13.05.15.
 *
 */
public class ParsersTest {
    @Test
    public void testTermSimple() {
        assertEquals(new Div<>(new Con<>(1), new Con<>(2)),
                Prs.term().parse("(1/2)").get(0).fst);
    }
    @Test
    public void testPrsLetterInd() {
        assertEquals((Character) 'N',
                Prs.letter.parse("Napis").get(0).fst);
    }
    @Test
    public void testPrsLetterNull() {
        assertEquals(new ListMonad<Pair<Character,String>>(),
                Prs.letter.parse("1234"));
    }
}
