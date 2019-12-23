package Elements;
import Tools.*;

import java.math.RoundingMode;
import java.util.*;
import java.math.BigDecimal;

public class Animal implements IWorldMapElement, IObservable{

    private Vector2d position;
    private Direction orientation;
    private WorldMap map;
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private Genotype genes;
    private double energy;
    private int age;
    private int numOfChildren;


    public Animal (WorldMap map, Vector2d initialPosition, double energy) {
        this.map = map;
        this.position = initialPosition;
        this.orientation = Direction.randomEnum();
        this.energy = energy;
        this.genes = new Genotype();
        this.age = 0;
        this.numOfChildren = 0;
    }

    public Animal (WorldMap map, Vector2d childPosition, double childEnergy, Genotype childGenotype) {
        this.map = map;
        this.position = childPosition;
        this.orientation = Direction.randomEnum();
        this.energy = childEnergy;
        this.genes = childGenotype;
        this.age = 0;
        this.numOfChildren = 0;
    }

    //Getters
    public Vector2d getPosition(){
        return this.position;
    }
    public Direction getOrientation() { return this.orientation;}
    public Genotype getGenotype() { return this.genes;}
    public int getAge() { return this.age; }
    public int getNumOfChildren() { return numOfChildren; }

    public double getEnergy() {
        BigDecimal tmp = new BigDecimal(this.energy);
        double roundedEnergy = tmp.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        return roundedEnergy;
    }

    public void changeEnergy(double change) {
        this.energy += change;
        if(this.energy < 0) {
            this.energy = 0;
        }
    }

    public Animal multiply (Animal partner) {

        //set energy and position for a child
        double childEnergy = (this.getEnergy()*0.25 + partner.getEnergy()*0.25);
        Vector2d childPosition = this.position.add(Direction.randomEnum().toUnitVector());
        childPosition = map.crossBounds(childPosition);
        Genotype childGenes = new Genotype(this.genes, partner.genes);
        Animal child = new Animal(map, childPosition, childEnergy, childGenes);

        this.changeEnergy(-(int)(0.25*this.getEnergy()));
        partner.changeEnergy(-(int)(0.25*partner.getEnergy()));

        return child;
    }

    public void rotate(int gen) {
        for (int i=0; i<gen; i++) {
            this.orientation = this.orientation.next();
        }
    }


    public void move() {
        Vector2d oldPosition = this.getPosition();
        this.position = this.position.add(this.orientation.toUnitVector());
        this.position = this.map.crossBounds(this.position); //check if an animal crossed bounds
        this.map.updatePosition(this, oldPosition, this.getPosition());
    }

    public void getOlder() {
        this.age += 1;
    }

    public void oneMoreChild() {
        this.numOfChildren += 1;
    }


    public String toString() {
        if (energy < 10 ) { return " " + Double.toString(getEnergy()); }
        else { return Double.toString(getEnergy()); }
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver obs : observers) {
            obs.updatePosition(this, oldPosition, newPosition);
        }
    }



}

