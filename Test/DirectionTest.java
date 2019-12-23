import Tools.Direction;
import Tools.Vector2d;
import org.junit.Assert;
import org.junit.Test;

public class DirectionTest {
    @Test
    public void nextTest(){
        Assert.assertEquals(Direction.N.next(), Direction.NE);
        Assert.assertEquals(Direction.NE.next(), Direction.E);
        Assert.assertEquals(Direction.E.next(), Direction.SE);
        Assert.assertEquals(Direction.SE.next(), Direction.S);
        Assert.assertEquals(Direction.S.next(), Direction.SW);
        Assert.assertEquals(Direction.SW.next(), Direction.W);
        Assert.assertEquals(Direction.W.next(), Direction.NW);
        Assert.assertEquals(Direction.NW.next(), Direction.N);
    }

    @Test
    public void previousTest(){
        Assert.assertEquals(Direction.N.previous(), Direction.NW);
        Assert.assertEquals(Direction.NE.previous(), Direction.N);
        Assert.assertEquals(Direction.E.previous(), Direction.NE);
        Assert.assertEquals(Direction.SE.previous(), Direction.E);
        Assert.assertEquals(Direction.S.previous(), Direction.SE);
        Assert.assertEquals(Direction.SW.previous(), Direction.S);
        Assert.assertEquals(Direction.W.previous(), Direction.SW);
        Assert.assertEquals(Direction.NW.previous(), Direction.W);
    }

    @Test
    public void toUnitVectorTest(){
        Assert.assertEquals(new Vector2d(0,1),Direction.N.toUnitVector());
        Assert.assertEquals(new Vector2d(1,1),Direction.NE.toUnitVector());
        Assert.assertEquals(new Vector2d(1,0),Direction.E.toUnitVector());
        Assert.assertEquals(new Vector2d(1,-1),Direction.SE.toUnitVector());
        Assert.assertEquals(new Vector2d(0,-1),Direction.S.toUnitVector());
        Assert.assertEquals(new Vector2d(-1,-1),Direction.SW.toUnitVector());
        Assert.assertEquals(new Vector2d(-1,0),Direction.W.toUnitVector());
        Assert.assertEquals(new Vector2d(-1,1),Direction.NW.toUnitVector());
    }
}
