import dinosaur.DinosaurGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage)
    {
        // Create game
        DinosaurGame game = new DinosaurGame();
        Scene scene = new Scene(game);

        // Initialize stage
        stage.setTitle("NEAT");
        stage.setScene(scene);
        stage.show();
    }

}
