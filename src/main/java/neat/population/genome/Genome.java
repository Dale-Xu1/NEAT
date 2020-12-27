package neat.population.genome;

import java.util.ArrayList;
import java.util.List;

public class Genome
{

    private final List<Gene> genes = new ArrayList<>();

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
        // Copy data
        for (Gene gene : genome.genes)
        {
            genes.add(new Gene(gene));
        }

        inputs = genome.inputs;
        outputs = genome.outputs;

        nodes = genome.nodes;
    }


    public List<Gene> getGenes()
    {
        return genes;
    }

    public int getInputs()
    {
        return inputs;
    }

    public int getOutputs()
    {
        return outputs;
    }

    public int getNodes()
    {
        return nodes;
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


    private void addConnection()
    {

    }

    private void addNode()
    {

    }

}
