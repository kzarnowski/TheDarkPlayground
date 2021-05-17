package Visualization;

import Tools.Vector2d;
import Elements.Animal;
import Elements.Grass;
import Tools.WorldMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RenderPanel extends JPanel {
    public WorldMap map;
    public JFrame frame;
    private Image jungleGrass;
    private Image jungle;
    private Image steppe;
    private Image steppeGrass;
    private Image animal1;
    private Image animal2;
    private Image animal3;
    private Image animal4;
    private Image animal5;
    private int width;
    private int height;
    private int widthScale;
    private int heightScale;

    public RenderPanel(WorldMap map, JFrame frame){
        this.map = map;
        this.frame = frame;
        this.setSize((int) ((frame.getWidth()) * 0.5), frame.getHeight() -38);
        this.width = this.getWidth();
        this.height = this.getHeight();
        this.widthScale = width / map.getWorldWidth();
        this.heightScale = height / map.getWorldHeight();

        try {
            this.animal1 = ImageIO.read(new File("src/Images/animal1.png"));
            this.animal2 = ImageIO.read(new File("src/Images/animal2.png"));
            this.animal3 = ImageIO.read(new File("src/Images/animal3.png"));
            this.animal4 = ImageIO.read(new File("src/Images/animal4.png"));
            this.animal5 = ImageIO.read(new File("src/Images/animal5.png"));
            this.jungleGrass = ImageIO.read(new File("src/Images/jungleGrass.png"));
            this.steppeGrass = ImageIO.read(new File("src/Images/steppeGrass.png"));
            this.jungle = ImageIO.read(new File("src/Images/jungle.png"));
            this.steppe = ImageIO.read(new File("src/Images/steppe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.animal1 = this.animal1.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.animal2 = this.animal2.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.animal3 = this.animal3.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.animal4 = this.animal4.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.animal5 = this.animal5.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.jungleGrass = this.jungleGrass.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.jungle = this.jungle.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.steppe = this.steppe.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
        this.steppeGrass = this.steppeGrass.getScaledInstance(widthScale, heightScale, Image.SCALE_DEFAULT);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.width, this.height);

        for (int x = 0; x < this.map.getWorldWidth(); x++){
            for (int y = 0; y < this.map.getWorldHeight(); y++){
                Vector2d position = new Vector2d(x,y);
                //draw background
                if (map.isInJungle(position)){
                    g.drawImage(this.jungle, x * widthScale, y * heightScale, this);
                }
                else{
                    g.drawImage(this.steppe, x * widthScale, y * heightScale, this);
                }

                //draw grass
                if (map.getGrassMap().containsKey(position) && map.isInJungle(new Vector2d(x,y))){
                    g.drawImage(this.jungleGrass, x * widthScale, y * heightScale, this);
                }
                if (map.getGrassMap().containsKey(position) && !map.isInJungle(new Vector2d(x,y))){
                    g.drawImage(this.steppeGrass, x * widthScale, y * heightScale, this);
                }

                //draw animals
                if (map.getAnimalsMap().containsKey(position)){
                    int sumEnergy = 0;
                    for (Animal an : map.getAnimalsMap().get(position)) {
                        sumEnergy += an.getEnergy();
                    }

                    //5 different colors due to energy level
                    if (sumEnergy <= map.getStartEnergy()/2) {
                        g.drawImage(this.animal1, x * widthScale, y * heightScale, this);
                    }
                    else if (sumEnergy <= map.getStartEnergy()) {
                        g.drawImage(this.animal2, x * widthScale, y * heightScale, this);
                    }
                    else if (sumEnergy <= map.getStartEnergy()*2) {
                        g.drawImage(this.animal3, x * widthScale, y * heightScale, this);
                    }
                    else if (sumEnergy <= map.getStartEnergy()*3.5) {
                        g.drawImage(this.animal4, x * widthScale, y * heightScale, this);
                    }
                    else {
                        g.drawImage(this.animal5, x * widthScale, y * heightScale, this);
                    }
                }
            }
        }
    }

}
