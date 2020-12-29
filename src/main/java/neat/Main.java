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


    private final Population population = new Population(110); // 4156
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
        Genome genome1 = population.getBest();
        for (int i = 0; i < 100; i++) genome1.mutate();

        Genome genome2 = new Genome(genome1);
        for (int i = 0; i < 100; i++)
        {
            genome1.mutate();
            genome2.mutate();
        }

        new GenomeRenderer(genome1).render(gc, 0, 0, 360, 240);
        new GenomeRenderer(genome2).render(gc, 360, 0, 360, 240);

        Genome child = new Genome(genome1);
        child.crossover(genome2);

        new GenomeRenderer(child).render(gc, 180, 240, 360, 240);

        Network network = new Network(genome2);

        for (int i = 0; i < 10; i++)
        {
            double[] output = network.predict(new double[] { 0.6, 0.2, 0.4 });
            for (double value : output) System.out.print(value + " ");

            System.out.println();
        }
    }

}
