package Elements;

import Tools.IPositionChangeObserver;
import Tools.Vector2d;

public interface IObservable {

    void addObserver(IPositionChangeObserver observer);

    void removeObserver(IPositionChangeObserver observer);

    void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
