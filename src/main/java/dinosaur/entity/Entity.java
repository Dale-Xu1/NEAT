package dinosaur.entity;

import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity
{

    protected Vector2 position;
    protected Vector2 dimensions;


    protected Entity(Vector2 position, Vector2 dimensions)
    {
        this.position = position;
        this.dimensions = dimensions;
    }


    public abstract void update(float delta);

    public abstract void render(GraphicsContext gc);


    public boolean isIntersecting(Entity entity)
    {
        // AABB collision detection
        return (position.x < entity.position.x + entity.dimensions.x) &&
               (position.x + dimensions.x > entity.position.x) &&
               (position.y < entity.position.y + entity.dimensions.y) &&
               (position.y + dimensions.y > entity.position.y);
    }

}
