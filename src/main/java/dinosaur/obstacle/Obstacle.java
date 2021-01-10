package dinosaur.obstacle;

import dinosaur.entity.Entity;
import dinosaur.math.Vector2;

public abstract class Obstacle extends Entity
{

    private static final float SCALE = 100;
    private static final Vector2 VELOCITY = new Vector2(-2.5f, 0).mult(SCALE);


    private final ObstacleSpawner spawner;


    protected Obstacle(ObstacleSpawner spawner, Vector2 position, Vector2 dimensions)
    {
        super(position, dimensions);
        this.spawner = spawner;
    }


    @Override
    public void update(float delta)
    {
        position = position.add(VELOCITY.mult(spawner.getSpeed() * delta));
    }

    public boolean isDead()
    {
        return position.x < -dimensions.x;
    }

}
