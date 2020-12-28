package neat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import neat.network.Network;
import neat.population.Population;
import neat.population.genome.Genome;
import neat.population.genome.render.GenomeRenderer;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch();
    }


    private final Population population = new Population(0);
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
        Genome genome = population.getBest();
        genome.mutate();

        Network network = new Network(genome);

        GenomeRenderer renderer = new GenomeRenderer(genome);
        renderer.render(gc, 0, 0, 720, 480);
    }

}
