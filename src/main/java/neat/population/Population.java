package neat.population;

import neat.population.genome.Genome;
import neat.population.innovation.History;

import java.util.Random;

public class Population
{

    public static final double DISJOINT = 1; // How much disjoint genes contribute to distance
    public static final double WEIGHTS = 0.4; // How much weight differences contribute to distance
    public static final double THRESHOLD = 3; // Distance threshold for whether is genome is part of a species

    public static final double MUTATE_WEIGHT = 0.8; // Chance of mutating a weight
    public static final double RESET_WEIGHT = 0.1; // Chance of resetting a weight (When it is being mutated)
    public static final double ADD_CONNECTION = 0.05; // Chance of a connection being added
    public static final double ADD_NODE = 0.03; // Chance of a node being added between a connection

    public static final double NO_CROSSOVER = 0.25; // Chance to mutate without crossover
    public static final double DISABLE_GENE = 0.75; // Chance a gene is disabled if it is disabled in either parent


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


    public History getHistory()
    {
        return history;
    }

    public Random getRandom()
    {
        return random;
    }


    public Genome getBest()
    {
        return new Genome(this, 3, 2);
    }

}
