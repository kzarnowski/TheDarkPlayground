package Elements;

import Tools.Vector2d;

public class Grass implements IWorldMapElement {

    private Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return " x";
    }
}

