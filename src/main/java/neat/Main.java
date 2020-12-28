package neat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import neat.network.Network;
import neat.population.Population;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch();
    }


    private final Population population = new Population();

    private GraphicsContext gc;


    @Override
    public void start(Stage stage)
    {
        // Create canvas
        Canvas canvas = new Canvas(720, 480);
        Group root = new Group(canvas);

        gc = canvas.getGraphicsContext2D();
        run();

        // Initialize stage
        stage.setTitle("NEAT");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void run()
    {
        Network network = new Network(population.getBest());
        network.render(gc, 100, 100, 300, 200);
    }

}
