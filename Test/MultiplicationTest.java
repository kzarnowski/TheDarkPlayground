import Elements.Animal;
import Tools.Vector2d;
import Tools.WorldMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MultiplicationTest {

    @Test
    public void multiplicationGenotypeTest() {
        WorldMap map = new WorldMap(3,3,0.1,5,5,5);

        for (int n=0; n<100; n++) {
            Animal parent1 = new Animal(map, new Vector2d(2, 2), 10);
            Animal parent2 = new Animal(map, new Vector2d(2, 2), 10);
            Animal child = parent1.multiply(parent2);

            //checking energy level
            Assert.assertEquals(child.getEnergy(), 5, 0.01);

            //checking if any gene is missing
            boolean[] isInGenes = new boolean[8];
            Arrays.fill(isInGenes, false);

            for (int gen : child.getGenotype().getGenes()) {
                isInGenes[gen] = true;
            }

            for (int i = 0; i < 8; i++) {
                Assert.assertTrue(isInGenes[i]);
            }
        }
    }
}
