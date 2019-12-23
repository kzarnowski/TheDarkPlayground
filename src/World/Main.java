package World;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Playground TheDarkPlayground = new Playground();
        TheDarkPlayground.startSimulation();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

