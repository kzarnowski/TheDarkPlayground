package Tools;

import java.util.*;

public enum Direction {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;

    public Direction next() {
        switch(this) {
            case N: return NE;
            case NE: return E;
            case E: return SE;
            case SE: return S;
            case S: return SW;
            case SW: return W;
            case W: return NW;
            case NW: return N;
        }
        return null;
    }

    public Direction previous() {
        switch(this) {
            case N: return NW;
            case NE: return N;
            case E: return NE;
            case SE: return E;
            case S: return SE;
            case SW: return S;
            case W: return SW;
            case NW: return W;
        }
        return null;
    }

    public Vector2d toUnitVector() {
        switch(this) {
            case N: return new Vector2d(0,1);
            case NE: return new Vector2d(1,1);
            case E: return new Vector2d(1,0);
            case SE: return new Vector2d(1,-1);
            case S: return new Vector2d(0,-1);
            case SW: return new Vector2d(-1,-1);
            case W: return new Vector2d(-1,0);
            case NW: return new Vector2d(-1,1);
        }
        return new Vector2d(0,0);
    }

    public static Direction randomEnum(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public String toString() {

        switch (this) {
            case N:  return "↑";
            case S:  return "↓";
            case W:  return "←";
            case E:  return "→";
            case NW: return "↖";
            case SW: return "↙";
            case NE: return "↗";
            case SE: return "↘";
        }
        return null;
    }

}

