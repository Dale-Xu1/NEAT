package dinosaur.obstacle;

import dinosaur.math.Random;

import java.util.List;

public class ObstacleSpawner
{

    private static final float SPEED_INCREASE = 0.02f;
    private static final double BIRD_CHANCE = 0.1;


    private static float randomDelay()
    {
        return Random.next() * 1.5f + 0.5f;
    }


    private final List<Obstacle> obstacles;
    private final int width;

    private float delay;
    private float speed;


    public ObstacleSpawner(List<Obstacle> obstacles, int width)
    {
        this.obstacles = obstacles;
        this.width = width;

        reset();
    }


    public void update(float delta)
    {
        delay -= delta;
        speed += SPEED_INCREASE * delta;

        // Spawn cactus when delay runs out
        if (delay < 0)
        {
            Obstacle obstacle = (Random.next() < BIRD_CHANCE) ?
                new Bird(this, width) :
                new Cactus(this, width);

            delay = randomDelay();
            obstacles.add(obstacle);
        }
    }

    public void reset()
    {
        obstacles.clear();

        delay = randomDelay();
        speed = 1;
    }


    public float getSpeed()
    {
        return speed;
    }

}
