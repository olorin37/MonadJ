import org.junit.Test;
import static org.junit.Assert.assertEquals;
import Primitives.Div;
import Primitives.Con;

/**
 * Created by olorin on 13.05.15.
 */
public class ParsersTest {
    @Test
    public void testTermSimple() {
        assertEquals( new Div<>(new Con<>(1), new Con<>(2)),
                      Prs.term().parse("(1/2)") );
    }
}
