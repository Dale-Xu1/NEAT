package dinosaur;

import dinosaur.entity.*;
import dinosaur.obstacle.Obstacle;
import dinosaur.obstacle.ObstacleSpawner;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import neat.NEAT;
import neat.genome.Genome;
import neat.genome.render.GenomeRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DinosaurGame extends Group
{

    private class Timer extends AnimationTimer
    {
        private long previous;
        private long accumulated = 0;

        @Override
        public void start()
        {
            super.start();
            previous = System.currentTimeMillis();
        }

        @Override
        public void handle(long now)
        {
            // Accumulate lagged time
            long current = System.currentTimeMillis();

            accumulated += current - previous;
            previous = current;

            // Update and render
            int delay = (int) (delta * 1000);
            while (accumulated > delay)
            {
                update();
                accumulated -= delay;
            }

            render();
        }
    }


    private final Timer timer = new Timer();
    private final GraphicsContext gc;

    private final int width = 720;
    private final int height = 360;

    private final float delta = 0.02f;

    private final NEAT neat = new NEAT(500, 5, 2);

    private final List<Obstacle> obstacles = new ArrayList<>();
    private final Dinosaur[] dinosaurs = new Dinosaur[neat.getGenomes().length];

    private final ObstacleSpawner spawner = new ObstacleSpawner(obstacles, width);


    public DinosaurGame()
    {
        // Create canvas
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        getChildren().add(canvas);

        // Initialize dinosaurs
        Genome[] genomes = neat.getGenomes();
        for (int i = 0; i < dinosaurs.length; i++)
        {
            dinosaurs[i] = new Dinosaur(genomes[i], obstacles);
        }

        // Start timer
        timer.start();
    }


    public void start()
    {
        timer.start();
    }


    private void update()
    {
        spawner.update(delta);

        // Update entities
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext())
        {
            Obstacle obstacle = iterator.next();
            obstacle.update(delta);

            // Remove obstacle if dead
            if (obstacle.isDead())
            {
                iterator.remove();
            }
        }

        for (Dinosaur dinosaur : dinosaurs)
        {
            // Update dinosaur if it isn't dead
            if (dinosaur.isAlive())
            {
                dinosaur.update(delta);
            }
        }
    }

    private void render()
    {
        // Clear screen
        gc.clearRect(0, 0, width, height);

        // Make bottom-left the origin
        gc.save();
        gc.translate(0, height - 20); // -20 to lift the ground up
        gc.scale(1, -1);

        // Draw ground
        gc.setStroke(Color.BLACK);
        gc.strokeLine(0, 0, width, 0);

        // Render entities
        for (Entity obstacle : obstacles)
        {
            obstacle.render(gc);
        }

        for (Dinosaur dinosaur : dinosaurs)
        {
            if (dinosaur.isAlive()) dinosaur.render(gc);
        }

        gc.restore();

        GenomeRenderer renderer = new GenomeRenderer(neat.getBest());
        renderer.render(gc, 280, 15, 400, 200);
    }

}
