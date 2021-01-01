package dinosaur.entity;

import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cactus extends Entity
{

    public Cactus()
    {
        super(new Vector2(0, 0), new Vector2(100, 100));
    }


    @Override
    public void update(float delta)
    {

    }

    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFill(Color.color(0, 1, 0));
        gc.fillRect(position.x, position.y, dimensions.x, dimensions.y);
    }

}