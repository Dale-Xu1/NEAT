package dinosaur.obstacle;

import dinosaur.math.Random;
import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cactus extends Obstacle
{

    private static Vector2 createDimensions()
    {
        float y = Random.next();
        float x = Random.next() * (1 - y); // Higher cacti are thinner on average

        return new Vector2(x * 15 + 25, y * 15 + 35);
    }


    public Cactus(ObstacleSpawner spawner, int width)
    {
        super(spawner, new Vector2(width, 0), createDimensions());
    }


    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFill(Color.color(0, 1, 0));
        gc.fillRect(position.x, position.y, dimensions.x, dimensions.y);
    }

}
