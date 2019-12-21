package Tools;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJSON {
    @SuppressWarnings("unchecked")
    public static void createJSON()
    {
        //parameters
        JSONObject details = new JSONObject();
        details.put("worldWidth", "200");
        details.put("worldHeight", "200");
        details.put("jungleRatio", "0.2");
        details.put("startEnergy", "10");
        details.put("moveEnergy", "1");
        details.put("grassEnergy", "2");
        details.put("ancestors", "20");
        //details.put("days", "200");
        //details.put("refresh", "50");


        try (FileWriter file = new FileWriter("parameters.json")) {
            file.write(details.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
