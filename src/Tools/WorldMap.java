package Tools;


import java.util.*;
import Elements.*;

public class WorldMap implements IPositionChangeObserver {

    private Vector2d upperRight;
    private Vector2d lowerLeft;
    private Vector2d jungleUpperRight;
    private Vector2d jungleLowerLeft;
    private int worldWidth;
    private int worldHeight;
    private int jungleWidth;
    private int jungleHeight;

    private double moveEnergy;
    private double grassEnergy;
    private double startEnergy;
    private double multiplyEnergy;

    public Map<Vector2d, Grass> grassMap = new HashMap<>();
    public Map<Vector2d, LinkedList<Animal>> animalsMap = new HashMap<>();
    public LinkedList<Animal> animalsList;
    public LinkedList<Grass> grassList;
    public MapVisualizer mapVisualizer = new MapVisualizer(this);

    private int numOfDeaths;


    public WorldMap (int worldWidth, int worldHeight, double jungleRatio, double startEnergy, double moveEnergy, double grassEnergy) {
        this.startEnergy = startEnergy;
        this.grassList = new LinkedList<>();
        this.animalsList = new LinkedList<>();
        this.multiplyEnergy = startEnergy/2;
        this.grassEnergy = grassEnergy;
        this.moveEnergy = moveEnergy;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(worldWidth - 1, worldHeight - 1);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        //calculating jungle size and position

        this.jungleWidth = (int)(worldWidth * (Math.sqrt(jungleRatio)));
        this.jungleHeight = (int)(worldHeight * (Math.sqrt(jungleRatio)));

        int xMin = (worldWidth / 2) - (jungleWidth / 2);
        int yMin = (worldHeight / 2) - (jungleHeight / 2);

        jungleLowerLeft = new Vector2d(xMin, yMin);
        jungleUpperRight = new Vector2d(xMin + jungleWidth, yMin+jungleHeight);

        this.numOfDeaths = 0;

    }

    //Getters
    public Vector2d getLowerLeft() { return lowerLeft;}
    public Vector2d getUpperRight() { return upperRight;}
    public Vector2d getJungleUpperRight() { return jungleUpperRight;}
    public Vector2d getJungleLowerLeft() { return jungleLowerLeft;}
    public int getWorldWidth() { return this.worldWidth; }
    public int getWorldHeight() { return this.worldHeight; }
    public int getJungleWidth() { return this.jungleWidth; }
    public int getJungleHeight() { return this.jungleHeight; }
    public double getStartEnergy() { return this.startEnergy; }
    public double getMoveEnergy() { return this.moveEnergy; }
    public LinkedList<Animal> getAnimals(){
        return animalsList;
    }
    public LinkedList<Grass> getGrass(){
        return grassList;
    }
    public Map<Vector2d, Grass> getGrassMap() { return grassMap; }
    public Map<Vector2d, LinkedList<Animal>> getAnimalsMap() { return animalsMap; }
    public int getNumOfDeaths() { return numOfDeaths; }


    public Vector2d crossBounds (Vector2d position) {
        //checks if a given vector is out of a map and place it on the other side
        Vector2d result = new Vector2d(position.x, position.y);

        if(position.x >= this.worldWidth) {
            result = new Vector2d(0,position.y);
        }
        if(position.x < 0) {
            result = new Vector2d(this.worldWidth - 1, position.y);
        }
        if(position.y >= this.worldHeight) {
            result = new Vector2d(position.x, 0);
        }
        if(position.y < 0) {
            result = new Vector2d(position.x, this.worldHeight - 1);
        }

        return result;
    }

    public boolean isInJungle(Vector2d position) {
        return (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight));
    }

    public boolean addAnimal (Animal an, Vector2d pos) {
        //add new animal in the world
        Vector2d p = crossBounds(pos);
        LinkedList<Animal> aL = animalsMap.get(p);
        if (aL == null) {
            LinkedList<Animal> tmp = new LinkedList<>();
            tmp.add(an);
            animalsMap.put(p, tmp);
        }
        else {
            aL.add(an);
            animalsMap.replace(p, aL);
        }
        return true;
    }

    public boolean removeAnimal (Animal an, Vector2d pos) {
        //remove animal from the world
        Vector2d p = crossBounds(pos);
        LinkedList<Animal> tmp = animalsMap.get(p);
        if (tmp == null || tmp.size() == 0) {
            throw new IllegalArgumentException("There is no animal on this position.");
        }
        else {
            tmp.remove(an);
            if (tmp.size() == 0) {
                animalsMap.remove(pos);
            }
            else {
                animalsMap.replace(pos, tmp);
            }

            an.removeObserver(this);
        }
        return true;
    }

    public boolean addGrass (Grass g) {
        if (grassMap.get(g.getPosition()) == null) {
            grassMap.put(g.getPosition(), g);
            grassList.add(g);
            return true;
        }
        return false;
    }

    public boolean moveAll(){

        for (Animal an : animalsList) {
            an.rotate((int) (Math.random() * 8));
            an.move();
        }
        return true;
    }

    public boolean place(IWorldMapElement element){
        Vector2d position = crossBounds(element.getPosition());

        if (element instanceof Grass) {
            addGrass((Grass) element);
            grassList.add((Grass) element);
        }
        if (element instanceof Animal) {
            addAnimal((Animal) element, position);
            animalsList.add((Animal) element);
            ((Animal) element).addObserver(this);
        }
        return true;
    };

    public boolean isOccupied(Vector2d position){
        return grassMap.containsKey(position) || animalsMap.containsKey(position);
    };

    public void multiplication() {

        ArrayList<Animal> children = new ArrayList<>();
        for ( LinkedList<Animal> aL : animalsMap.values()) {
            if (aL != null) {
                if (aL.size() >= 2) {
                    //taking two animals with the highest energy
                    aL.sort(Comparator.comparing(Animal::getEnergy).reversed());
                    Animal parent1 = aL.get(0);
                    Animal parent2 = aL.get(1);

                    if (parent1.getEnergy() >= this.multiplyEnergy && parent2.getEnergy() >= this.multiplyEnergy) {
                        Animal child = parent1.multiply(parent2);
                        children.add(child);
                        parent1.oneMoreChild();
                        parent2.oneMoreChild();
                    }

                }
            }
        }

        for (Animal baby : children) {
            place((IWorldMapElement) baby);
            System.out.println("Child was born at: " + baby.getPosition().toString() + ". ");
        }
    }

    public void eating() {

        LinkedList<Grass> eatenGrass = new LinkedList<>();
        for (Grass g : grassList) {
            LinkedList<Animal> hungryAnimals = animalsMap.get(g.getPosition());
            if(hungryAnimals != null && hungryAnimals.size() >= 1) {
                hungryAnimals.sort(Comparator.comparing(Animal::getEnergy).reversed());
                Animal mostHungry = hungryAnimals.getFirst();
                mostHungry.changeEnergy(grassEnergy);
                eatenGrass.add(g);
                System.out.println("Grass eaten: " + g.getPosition().toString() + ". ");
            }
        }

        for (Grass g : eatenGrass) {
            grassList.remove(g);
            grassMap.remove(g.getPosition());
        }
    }

    public int removeDeadAnimals() {
        //returns summed up lifetime of dying animals
        int totalLifeTime = 0;
        LinkedList<Animal> l = getAnimals();
        for (int i = 0; i < l.size(); i++) {
            Animal a = animalsList.get(i);
            if (a.getEnergy() == 0) {
                totalLifeTime += a.getAge();
                this.numOfDeaths += 1;
                System.out.println("Death at: " + a.getPosition() + ". ");
                removeAnimal(a, a.getPosition());
                a.removeObserver(this);
                animalsList.remove(a);
            }
        }
        return totalLifeTime;
    }

    public void growGrass(){
        //set good approximation when to stop searching for free fields when the map is almost full
        int jungleSearchLimit = 10*((jungleWidth * jungleHeight) - grassList.size()/2);
        int steppeSearchLimit = 10*((worldWidth * worldHeight) - (jungleWidth * jungleHeight) - grassList.size()/2);

        //jungle
        for (int i=0; i<jungleSearchLimit; i++) {
            Vector2d randField = new Vector2d((int)(Math.random()*jungleWidth), (int)(Math.random()*jungleHeight))
                    .add(jungleLowerLeft);
            if (!grassMap.containsKey(randField) && !animalsMap.containsKey(randField)) {
                Grass grown = new Grass(randField);
                addGrass(grown);
                break;
            }
        }

        //steppe
        for (int i=0; i<steppeSearchLimit; i++) {
            Vector2d randField = new Vector2d((int)(Math.random()*worldWidth), (int)(Math.random()*worldHeight));
            if (!grassMap.containsKey(randField) && !animalsMap.containsKey(randField)) {
                Grass grown = new Grass(randField);
                addGrass(grown);
                break;
            }
        }
    }


    @Override
    public String toString() {
        return this.mapVisualizer.draw(lowerLeft, upperRight);
    }

    @Override
    public void updatePosition (Object an, Vector2d oldPosition, Vector2d newPosition) {
        oldPosition = crossBounds(oldPosition);
        newPosition = crossBounds(newPosition);
        removeAnimal((Animal)an, oldPosition);
        addAnimal((Animal)an, newPosition);
    }
}
