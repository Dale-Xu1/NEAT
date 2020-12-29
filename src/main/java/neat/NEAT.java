package neat;

import neat.genome.Genome;
import neat.genome.innovation.History;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NEAT
{

    public static final int MIN_NORMAL = 20; // Minimum gene length where normalizer is 1
    public static final double DISJOINT = 1; // How much disjoint genes contribute to distance
    public static final double WEIGHTS = 0.4; // How much weight differences contribute to distance
    public static final double THRESHOLD = 3; // Distance threshold for whether is genome is part of a species

    public static final double MUTATE_WEIGHT = 0.8; // Chance of mutating a weight
    public static final double RESET_WEIGHT = 0.1; // Chance of resetting a weight (When it is being mutated)
    public static final double SHIFT_WEIGHT = 0.05; // Coefficient of gaussian distribution when weight is shifted
    public static final double ADD_CONNECTION = 0.05; // Chance of a connection being added
    public static final double ADD_NODE = 0.03; // Chance of a node being added between a connection

    public static final double NO_CROSSOVER = 0.25; // Chance to mutate without crossover
    public static final double DISABLE_GENE = 0.75; // Chance a gene is disabled if it is disabled in either parent


    private final History history = new History();
    private final Random random;

    private final List<Species> species = new ArrayList<>();


    public NEAT(long seed)
    {
        random = new Random(seed);
    }

    public NEAT()
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
        return null;
    }

    public void nextGeneration()
    {

    }

}
