package neat.genome;

import neat.NEAT;
import neat.genome.innovation.History;

import java.util.Random;

public class Gene
{

    private final Random random;

    private final int in;
    private final int out;

    private double weight;
    private boolean enabled = true;

    private final int innovation;


    public Gene(NEAT neat, int in, int out, double weight)
    {
        random = neat.getRandom();

        this.in = in;
        this.out = out;

        this.weight = weight;

        // Get innovation number
        History history = neat.getHistory();
        this.innovation = history.getNumber(in, out);
    }

    public Gene(NEAT neat, int in, int out)
    {
        this(neat, in, out, 0);
        weight = randomWeight();
    }

    public Gene(Gene gene)
    {
        // Copy gene data
        random = gene.random;

        in = gene.in;
        out = gene.out;

        weight = gene.weight;
        enabled = gene.enabled;

        innovation = gene.innovation;
    }

    private double randomWeight()
    {
        return random.nextDouble() * 2 - 1;
    }


    public int getIn()
    {
        return in;
    }

    public int getOut()
    {
        return out;
    }

    public double getWeight()
    {
        return weight;
    }

    public boolean isDisabled()
    {
        return !enabled;
    }

    public void disable()
    {
        enabled = false;
    }

    public int getInnovation()
    {
        return innovation;
    }


    public void mutate()
    {
        // Either reset or randomly shift weight
        if (random.nextDouble() < NEAT.RESET_WEIGHT) weight = randomWeight();
        else weight += random.nextGaussian() * NEAT.SHIFT_WEIGHT;
    }

    public void crossover(Gene gene)
    {
        // Randomly select weight between two parents
        if (random.nextBoolean()) weight = gene.weight;

        // Gene is disabled with a set chance if either parent is disabled
        enabled = (enabled && gene.enabled) || random.nextDouble() > NEAT.DISABLE_GENE;
    }

}
