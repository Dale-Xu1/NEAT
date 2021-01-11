package dinosaur.obstacle;

import dinosaur.math.Random;
import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bird extends Obstacle
{

    private static final Vector2 DIMENSIONS = new Vector2(50, 25);


    private static float getY()
    {
        return Random.next() * 50 + 5;
    }


    public Bird(ObstacleSpawner spawner, int width)
    {
        super(spawner, new Vector2(width, getY()), DIMENSIONS);
    }


    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFill(Color.color(0, 0, 1));
        gc.fillRect(position.x, position.y, dimensions.x, dimensions.y);
    }

}
