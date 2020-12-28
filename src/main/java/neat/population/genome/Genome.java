package neat.population.genome;

import neat.population.innovation.History;

import java.util.ArrayList;
import java.util.List;

public class Genome
{

    private final History history;
    private final List<Gene> genes = new ArrayList<>();

    private final int inputs;
    private final int outputs;

    private int nodes;


    public Genome(History history, int inputs, int outputs)
    {
        this.history = history;

        this.inputs = inputs;
        this.outputs = outputs;

        // Calculate number of nodes (Extra 1 is for bias node)
        nodes = inputs + outputs + 1;
        addGene();
    }

    private Genome(Genome genome)
    {
        history = genome.history;

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
        for (int i = 0; i < 10; i++)
        {
            if ((Math.random() < 0.5)) addGene();
            else addNode();
        }
    }

    public Genome crossover(Genome genome)
    {
        Genome child = new Genome(this);

        // TODO: Crossover genome
        return child;
    }


    private void addGene()
    {
        // Maximum gene length was reached
        if (genes.size() >= nodes * nodes) return;

        int in, out;
        do
        {
            // Select random node pair until one is valid
            in = (int) Math.floor(Math.random() * nodes);
            out = (int) Math.floor(Math.random() * nodes);
        }
        while (!isValid(in, out));

        genes.add(new Gene(history, in, out));
    }

    private boolean isValid(int in, int out)
    {
        // Tests if gene already exists
        for (Gene gene : genes)
        {
            if (gene.getIn() == in && gene.getOut() == out) return false;
        }

        return true;
    }

    private void addNode()
    {
        // Select random gene
        Gene gene = genes.get((int) Math.floor(Math.random() * genes.size()));
        gene.disable();

        int node = nodes++; // Create node

        // Create new connections
        genes.add(new Gene(history, gene.getIn(), node, 1));
        genes.add(new Gene(history, node, gene.getOut(), gene.getWeight()));
    }

}
