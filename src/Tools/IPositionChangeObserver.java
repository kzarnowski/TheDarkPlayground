package Tools;

public interface IPositionChangeObserver {
    void updatePosition(Object o, Vector2d oldPosition, Vector2d newPosition);
}
