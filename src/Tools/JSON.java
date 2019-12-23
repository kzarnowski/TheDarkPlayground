package Tools;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class JSON {
    public final Integer worldWidth;
    public final Integer worldHeight;
    public final Double jungleRatio;
    public final Double startEnergy;
    public final Double moveEnergy;
    public final Double grassEnergy;
    public final Integer ancestors;
    public final Integer simulationTime;
    public final Integer animationSpeed;


    public JSON(){
        this("parameters.json");
    }

    //reading simulation parameters from json file

    public JSON(String fileName) {
        Integer worldWidth = null;
        Integer worldHeight = null;
        Double jungleRatio = null;
        Double startEnergy = null;
        Double moveEnergy = null;
        Double grassEnergy = null;
        Integer ancestors = null;
        Integer simulationTime = null;
        Integer animationSpeed = null;

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            worldWidth = ((Long)(jsonObject.get("worldWidth"))).intValue();
            worldHeight = ((Long)(jsonObject.get("worldHeight"))).intValue();
            jungleRatio = (Double)jsonObject.get("jungleRatio");
            startEnergy = (Double)jsonObject.get("startEnergy");
            moveEnergy = (Double)jsonObject.get("moveEnergy");
            grassEnergy = (Double)jsonObject.get("grassEnergy");
            ancestors = ((Long)(jsonObject.get("ancestors"))).intValue();
            simulationTime = ((Long) jsonObject.get("simulationTime")).intValue();
            animationSpeed = ((Long)(jsonObject.get("animationSpeed(1-5)"))).intValue();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Exception");

        }finally {
            this.worldWidth = worldWidth;
            this.worldHeight = worldHeight;
            this.jungleRatio = jungleRatio;
            this.startEnergy = startEnergy;
            this.moveEnergy = moveEnergy;
            this.grassEnergy = grassEnergy;
            this.ancestors = ancestors;
            this.simulationTime = simulationTime;
            this.animationSpeed = animationSpeed;
        }


    }


}
