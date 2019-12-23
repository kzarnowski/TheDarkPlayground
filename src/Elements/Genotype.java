package Elements;
import java.util.*;

public class Genotype {

    private int[] genes;
    private int size;
    private int numOfGenes;

    public Genotype() {
        //create default random genotype
        this.genes = new int[32];
        this.size  = 32;
        this.numOfGenes = 8;
        fillRandom();
        repair();
    }

    public Genotype(Genotype gen1, Genotype gen2) {

        this.genes = new int[32];
        this.size  = 32;
        this.numOfGenes = 8;

        //divide genotype in two random places
        int p1 = (int)(Math.random()*gen1.size*0.7) + 1;
        int p2 = p1 + (int)(Math.random()*(gen1.size - p1 - 1)) +1;

        for (int i=0; i<p1; i++) {
            this.genes[i] = gen1.genes[i];
        }
        for (int i=p1; i<p2; i++) {
            this.genes[i] = gen2.genes[i];
        }
        for (int i=p2; i<gen2.size - 1; i++){
            this.genes[i] = gen1.genes[i];
        }

        //chance of mutation, max 3 mutations
        int numOfMutations = (int)(Math.random()*9) - 5;
        if (numOfMutations < 1) numOfMutations = 0;
        for (int i=0; i<numOfMutations; i++) {
            int mutatedGene = (int)(Math.random()*gen1.size);
            this.genes[mutatedGene] = (gen1.genes[mutatedGene] + gen2.genes[mutatedGene])%8;
        }

        this.repair();
    }

    //Getters
    public int[] getGenes() { return genes; }
    public int getSize() { return size; }
    public int getNumOfGenes() { return numOfGenes; }


    public void fillRandom() {
        for (int i = 0; i < size; i++) {
            genes[i] = (int) (Math.random() * (numOfGenes));
        }
    }

    public void repair() {

        boolean flag = true;
        while (flag) {
            flag = false;

            boolean[] isInGenes = new boolean[numOfGenes + 1];
            Arrays.fill(isInGenes, false);

            for (int i = 0; i < size; i++) {
                isInGenes[this.genes[i]] = true;
            }
            for (int i = 0; i < numOfGenes; i++) {
                if (!isInGenes[i]) {
                    flag = true;
                }
            }

            if (flag) {
                for (int i = 0; i < numOfGenes; i++) {
                    if (!isInGenes[i]) {
                        genes[(int) (Math.random() * (size))] = i;
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(int i=0; i<size; i++) {
            result.append(genes[i]).append(" ");
        }
        return result.toString();
    }
}

