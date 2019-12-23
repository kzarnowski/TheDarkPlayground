package World;
import Tools.*;
import Elements.*;
import Visualization.RenderPanel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Playground {

    private WorldMap map;
    private int ancestors;
    private int simulationTime;
    private int day = 0;

    private int totalLifeTime;
    private int refreshTime;

    public Playground() {
        //setting simulation parameters from parameters.json file
        JSON parameters = new JSON();
        this.map = new WorldMap(parameters.worldWidth, parameters.worldHeight, parameters.jungleRatio,
                parameters.startEnergy, parameters.moveEnergy, parameters.grassEnergy);
        this.ancestors = parameters.ancestors;
        this.simulationTime = parameters.simulationTime;
        this.refreshTime = 250 / parameters.animationSpeed;
        this.totalLifeTime = 0;

        ArrayList<Animal> elements = new ArrayList<>();
        for (int i = 0; i < this.ancestors; i++) {
            Vector2d randField = new Vector2d((int) (Math.random() * map.getWorldWidth()), (int) (Math.random() * map.getWorldHeight()));

            elements.add(new Animal(map, randField, map.getStartEnergy()));
        }

        for (Animal e : elements) {
            map.place(e);
            System.out.println("Ancestor added at: " + e.getPosition().toString());
        }
    }


    public void nextDay() {
        //process all events taking place during one day
        totalLifeTime += map.removeDeadAnimals();
        map.moveAll();
        map.eating();
        map.multiplication();
        map.growGrass();

        for (LinkedList<Animal> aL : map.animalsMap.values()) {
            if (aL != null && aL.size() >= 1) {
                for (Animal an : aL) {
                    an.changeEnergy(-map.getMoveEnergy());
                    an.getOlder();
                }
            }
        }
        day++;
    }

    public void startSimulation() throws InterruptedException {
        //map visualization
        System.out.println("SimulationTime: " + simulationTime);
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(1,2));
        frame.setSize(1280,720); //1600x840
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RenderPanel panel = new RenderPanel(map, frame);
        frame.add(panel);
        frame.setVisible(true);
        frame.setLayout(new GridLayout());

        //statistics visualization
        JPanel stats = new JPanel();
        JLabel dayCount = new JLabel("Day: " + this.day);
        JLabel animalsCount = new JLabel("Animals: " + getNumOfAnimals());
        JLabel grassCount = new JLabel("Grass: " + getNumOfGrass());
        JLabel dominantGenotype = new JLabel("Dominant genotype: " + getDominantGenotype());
        JLabel avgEnergy = new JLabel("Average energy: " + getAvgEnergy());
        JLabel avgNumOfChildren = new JLabel("Average number of children: " + getAvgNumOfChildren());
        JLabel numOfDeaths = new JLabel( "Number of deaths: 0");
        JLabel avgLifeTime = new JLabel("Average lifetime: " + getAvgLifeTime());

        stats.add(dayCount);
        stats.add(animalsCount);
        stats.add(grassCount);
        stats.add(dominantGenotype);
        stats.add(avgEnergy);
        stats.add(avgNumOfChildren);
        stats.add(numOfDeaths);
        stats.add(avgLifeTime);
        stats.setLayout(new GridLayout(30, 1));
        stats.setVisible(true);

        frame.add(stats);


        java.util.concurrent.TimeUnit.SECONDS.sleep(2);

        for (int i = 1; i <= simulationTime; i++){
            //here is where actual simulation begins
            this.nextDay();
            panel.repaint();
            dayCount.setText("Day: " + this.day);
            animalsCount.setText("Animals: " + getNumOfAnimals());
            grassCount.setText("Grass: " + getNumOfGrass());
            dominantGenotype.setText("Dominant genotype: " + getDominantGenotype());
            avgEnergy.setText("Average energy: " + getAvgEnergy());
            avgNumOfChildren.setText("Average number of children: " + getAvgNumOfChildren());
            numOfDeaths.setText("Number of deaths: " + map.getNumOfDeaths());
            avgLifeTime.setText("Average lifetime: " + getAvgLifeTime());
            java.util.concurrent.TimeUnit.MILLISECONDS.sleep(this.refreshTime);
        }
    }




    //STATISTICS

    public int getDominantGenotype() {
        int[] genesPopularity = new int[8];
        Arrays.fill(genesPopularity, 0);
        for (Animal an : map.getAnimals()) {
            for (int i=0; i<32; i++) {
                genesPopularity[an.getGenotype().getGenes()[i]] += 1;
            }
        }


        int max = 0;
        int mostPopular = 0;
        for (int i=0; i<8; i++) {
            if (genesPopularity[i] > max) {
                max = genesPopularity[i];
                mostPopular = i;
            }
        }
        return mostPopular;
    }

    public double getAvgEnergy() {
        double result = 0;
        for (Animal an : map.getAnimals()) {
            result += an.getEnergy();
        }
        result /= map.getAnimals().size();
        BigDecimal tmp = new BigDecimal(result);
        result = tmp.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        return result;
    }

    public int getAvgLifeTime() {
        return (map.getNumOfDeaths() > 0 ? this.totalLifeTime / map.getNumOfDeaths() : 0);
    }

    public double getAvgNumOfChildren() {
        double result = 0;
        for (Animal an : map.getAnimals()) {
            result += an.getNumOfChildren();
        }
        result /= map.getAnimals().size();
        BigDecimal tmp = new BigDecimal(result);
        result = tmp.setScale(2,RoundingMode.HALF_DOWN).doubleValue();
        return result;
    }

    public int getNumOfAnimals() {
        return map.getAnimals().size();
    }

    public int getNumOfGrass() {
        return map.getGrass().size();
    }
}
