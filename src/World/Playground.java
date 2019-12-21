package World;
import Tools.*;
import Elements.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class Playground {

    private WorldMap map;
    private int ancestors;
    private int totalTime;

    public Playground() {
        JSON parameters = new JSON();

        this.map = new WorldMap(parameters.worldWidth, parameters.worldHeight, parameters.jungleRatio,
                parameters.startEnergy, parameters.moveEnergy, parameters.grassEnergy);
        this.ancestors = parameters.ancestors;
        this.totalTime = parameters.totalTime;

        ArrayList<IWorldMapElement> elements = new ArrayList<>();
        for (int i = 0; i < this.ancestors; i++) {
            Vector2d randField = new Vector2d((int) (Math.random() * map.getWorldWidth()), (int) (Math.random() * map.getWorldHeight()));
            System.out.println("Animal added at: " + randField.toString());
            elements.add(new Animal(map, randField, map.getStartEnergy()));
        }

        for (IWorldMapElement e : elements) {
            map.place(e);

        }
    }

    public void nextDay() {
        map.removeDeadAnimals();
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
    }

    public void startSimulation() {
        for (int i=0; i<totalTime; i++) {
            System.out.println(Integer.toString(i) + " animals: " + map.animalsList.size() +
                    " grass: " + map.grassList.size());
            nextDay();
        }
        System.out.println(this.map.toString());
    }
}
