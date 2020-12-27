package neat.population.genome;

import neat.population.innovation.History;

public class Gene
{

    private final int in;
    private final int out;

    private double weight;
    private final int innovation;


    public Gene(History history, int in, int out, double weight)
    {
        this.in = in;
        this.out = out;

        this.weight = weight;
        this.innovation = history.getNumber(in, out);
    }

    public Gene(History history, int in, int out)
    {
        this(history, in, out, 1); // TODO: Random values
    }

    public Gene(Gene gene)
    {
        in = gene.in;
        out = gene.out;

        weight = gene.weight;
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

    public int getInnovation()
    {
        return innovation;
    }


    public void mutate()
    {

    }

}
