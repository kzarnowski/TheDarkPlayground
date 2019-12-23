import org.junit.Assert;
import org.junit.Test;
import Elements.Genotype;

import java.util.Arrays;

public class GenotypeTest {

    @Test
    public void randomGenotypeTest() {

        //checking if any gene is missing
        for (int n=0; n<100; n++) {
            Genotype tmp = new Genotype();
            boolean[] isInGenes = new boolean[8];
            Arrays.fill(isInGenes, false);

            for (int gen : tmp.getGenes()) {
                isInGenes[gen] = true;
            }

            for (int i = 0; i < 8; i++) {
                Assert.assertTrue(isInGenes[i]);
            }
        }
    }



}
