package neat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch();
    }


    private static GraphicsContext gc;


    @Override
    public void start(Stage stage)
    {
        // Create canvas
        Canvas canvas = new Canvas(720, 480);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group(canvas);

        // Initialize stage
        stage.setTitle("NEAT");
        stage.setScene(new Scene(root));
        stage.show();

        gc.fillRect(100, 100, 200, 100);
    }

}
