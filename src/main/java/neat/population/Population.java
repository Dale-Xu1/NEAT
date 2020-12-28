package neat.population;

import neat.population.genome.Genome;
import neat.population.innovation.History;

import java.util.Random;

public class Population
{

    private final History history = new History();
    private final Random random;


    public Population(long seed)
    {
        random = new Random(seed);
    }

    public Population()
    {
        random = new Random();
    }


    public Genome getBest()
    {
        return new Genome(this, 3, 2);
    }


    public History getHistory()
    {
        return history;
    }

    public Random getRandom()
    {
        return random;
    }

}
