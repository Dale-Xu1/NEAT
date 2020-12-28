package neat.population.genome;

import neat.population.Population;
import neat.population.innovation.History;

import java.util.Random;

public class Gene
{

    private final int in;
    private final int out;

    private double weight;
    private boolean enabled = true;

    private final int innovation;


    public Gene(Population population, int in, int out, double weight)
    {
        this.in = in;
        this.out = out;

        this.weight = weight;

        // Get innovation number
        History history = population.getHistory();
        this.innovation = history.getNumber(in, out);
    }

    public Gene(Population population, int in, int out)
    {
        this(population, in, out, 0);

        // Set random weight
        Random random = population.getRandom();
        weight = random.nextDouble() * 2 - 1;
    }

    public Gene(Gene gene)
    {
        in = gene.in;
        out = gene.out;

        weight = gene.weight;
        enabled = gene.enabled;

        innovation = gene.innovation;
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

    }

    public Gene crossover(Gene gene)
    {
        Gene child = new Gene(this);
        return child;
    }

}
