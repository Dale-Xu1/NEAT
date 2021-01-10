package dinosaur.entity;

import java.util.List;

public class ObstacleSpawner
{

    private static final float SPEED_INCREASE = 0.02f;


    private static float randomDelay()
    {
        return (float) Math.random() * 1.5f + 0.5f;
    }


    private final List<Obstacle> obstacles;
    private final int width;

    private float delay = randomDelay();
    private float speed = 1;


    public ObstacleSpawner(List<Obstacle> obstacles, int width)
    {
        this.obstacles = obstacles;
        this.width = width;
    }


    public void update(float delta)
    {
        delay -= delta;
        speed += SPEED_INCREASE * delta;

        // Spawn cactus when delay runs out
        if (delay < 0)
        {
            delay = randomDelay();
            obstacles.add(new Cactus(this, width));
        }
    }


    public float getSpeed()
    {
        return speed;
    }

}
