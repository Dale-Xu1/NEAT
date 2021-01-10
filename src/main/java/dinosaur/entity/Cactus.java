package dinosaur.entity;

import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cactus extends Obstacle
{

    private static Vector2 getDimensions()
    {
        float y = (float) Math.random();
        float x = (float) Math.random() * (1 - y); // Higher cacti are thinner on average

        return new Vector2(x * 15 + 25, y * 15 + 35);
    }


    public Cactus(ObstacleSpawner spawner, int width)
    {
        super(spawner, getDimensions(), width);
    }


    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFill(Color.color(0, 1, 0));
        gc.fillRect(position.x, position.y, dimensions.x, dimensions.y);
    }

}
