package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;

public class BlockMapGui extends Application
{
    @Override
    public void start(Stage startingStage) throws Exception
    {
        Image backgroundImg = new Image(new FileInputStream("C:\\Users\\Goirick Saha\\Desktop\\FRC_2017_RoboMap.gif")); //You need to have the image location here

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        StackPane background = new StackPane();
        background.setBackground(new Background(backgroundImage));

        Scene menuScene = new Scene(background);
        startingStage.setScene(menuScene);
        //startingStage.setFullScreen(true);
        startingStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}