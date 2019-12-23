import Tools.WorldMap;
import Tools.Vector2d;
import Elements.Animal;
import Elements.Grass;

import org.junit.Assert;
import org.junit.Test;

public class GrassTest {
    @Test
    public void growUnderAnimalTest() {
        WorldMap map = new WorldMap(10, 10, 0.2, 10, 1, 5);
        map.place(new Animal(map, new Vector2d(2, 2), 10));
        map.place(new Animal(map, new Vector2d(8, 8), 10));
        map.place(new Animal(map, new Vector2d(1, 7), 10));
        map.place(new Animal(map, new Vector2d(6, 3), 10));
        for (int i = 0; i < 10000; i++) {
            for (Grass grass : map.grassList) {
                Assert.assertNotSame(new Vector2d(2, 2), grass.getPosition());
                Assert.assertNotSame(new Vector2d(8, 8), grass.getPosition());
                Assert.assertNotSame(new Vector2d(1, 7), grass.getPosition());
                Assert.assertNotSame(new Vector2d(6, 3), grass.getPosition());
            }
            map.growGrass();
        }
    }
}