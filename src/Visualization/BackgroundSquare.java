package Visualization;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BackgroundSquare extends Rectangle {
    private int height;
    private int width;
    private int x;
    private int y;
    private Color rectangleColor;

    public BackgroundSquare(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.rectangleColor = Color.web("#66ff66");
    }
}
