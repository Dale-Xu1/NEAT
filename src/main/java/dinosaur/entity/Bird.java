package dinosaur.entity;

import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bird extends Obstacle
{

    public Bird(ObstacleSpawner spawner, int width)
    {
        super(spawner, new Vector2(100, 100), width);
    }


    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFill(Color.color(0, 0, 1));
        gc.fillRect(position.x, position.y, dimensions.x, dimensions.y);
    }

}
