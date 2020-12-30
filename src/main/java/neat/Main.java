package neat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import neat.network.Network;
import neat.genome.Genome;
import neat.genome.render.GenomeRenderer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch();
    }


    private final NEAT neat = new NEAT(150, 2, 1, 0);
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
        for (int i = 0; i < 100; i++)
        {
            for (Genome genome : neat.getGenomes()) calculateFitness(genome);
            neat.nextGeneration();
        }
        for (Genome genome : neat.getGenomes()) calculateFitness(genome);

        Genome genome = neat.getBest();
        new GenomeRenderer(genome).render(gc, 0, 0, 720, 480);

        Network network = new Network(genome);

        for (int i = 0; i < 20; i++)
        {
            for (int j = 0; j < 20; j++)
            {
                double brightness = network.predict(new double[] { (double) i / 20, (double) j / 20 })[0];

                gc.setFill(Color.color(brightness, brightness, brightness));
                gc.fillRect(i * 5, j * 5, 5, 5);
            }
        }

        double a = network.predict(new double[] { 0, 0 })[0];
        double b = network.predict(new double[] { 1, 0 })[0];
        double c = network.predict(new double[] { 0, 1 })[0];
        double d = network.predict(new double[] { 1, 1 })[0];

        System.out.println(a + " " + b + " " + c + " " + d);
    }

    private void calculateFitness(Genome genome)
    {
        Network network = new Network(genome);

        List<double[]> inputs = Arrays.asList(new double[][]
        {
            { 0, 0,  0 },
            { 1, 0,  0 },
            { 0, 1,  0 },
            { 1, 1,  1 }
        });
        Collections.shuffle(inputs, neat.getRandom());

        double difference = 0;
        for (double[] input : inputs)
        {
            double prediction = network.predict(new double[] { input[0], input[1] })[0];
            difference += Math.abs(prediction - input[2]);
        }

        double fitness = Math.pow(inputs.size() - difference, 2);
        genome.setFitness(fitness);
    }

}
