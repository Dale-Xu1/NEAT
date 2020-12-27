package neat.population.genome;

import java.util.ArrayList;
import java.util.List;

public class Genome
{

    private final List<Connection> connections = new ArrayList<>();

    private final int inputs;
    private final int outputs;

    private int nodes;


    public Genome(int inputs, int outputs)
    {
        this.inputs = inputs;
        this.outputs = outputs;

        // Calculate number of nodes (Extra 1 is for bias node)
        nodes = inputs + outputs + 1;
    }

    public Genome(Genome genome)
    {
        // TODO: Copy connections

        inputs = genome.inputs;
        outputs = genome.outputs;

        nodes = genome.nodes;
    }


    public void mutate()
    {
        // TODO: Mutate genome
    }

    public Genome crossover(Genome genome)
    {
        Genome child = new Genome(this);

        // TODO: Crossover genome
        return child;
    }

}
