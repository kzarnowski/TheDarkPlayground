import org.junit.Assert;
import org.junit.Test;
import Tools.Vector2d;
import static org.junit.Assert.*;

public class Vector2dTest {

    Vector2d a = new Vector2d(2, 3);
    Vector2d b = new Vector2d(1,2);

    @Test
    public void equalsTest() {

        Vector2d c = new Vector2d(2,3);
        Assert.assertEquals(a,c);
        Assert.assertNotSame(a,b);
    }

    @Test
    public void toStringTest() {
        assertEquals(a.toString(), "(2,3)");
    }

    @Test
    public void precedesTest() {
        Vector2d c = new Vector2d(0,0);
        assertTrue(b.precedes(a));
        assertFalse(a.precedes(c));
    }

    @Test
    public void followsTest() {
        Vector2d c = new Vector2d(1,4);

        assertTrue(a.follows(b));
        assertFalse(a.follows(c));
    }

    @Test
    public void upperRightTest() {
        Vector2d c = new Vector2d(2,3);

        assertEquals(a.upperRight(b),c);
    }

    @Test
    public void lowerLeftTest() {
        Vector2d c = new Vector2d(1,2);

        assertEquals(a.lowerLeft(b),c);
    }

    @Test
    public void addTest() {
        Vector2d c = new Vector2d(3,5);

        assertEquals(a.add(b),c);
    }

    @Test
    public void subtractTest() {
        Vector2d c = new Vector2d(1,1);

        assertEquals(a.subtract(b),c);
    }

    @Test
    public void oppositeTest() {
        Vector2d c = new Vector2d(-2,-3);
        assertEquals(a.opposite(),c);
    }


    @Test
    public void oppositeTest2() {
        assertEquals(new Vector2d(-2,-3), a.opposite());
    }
}
