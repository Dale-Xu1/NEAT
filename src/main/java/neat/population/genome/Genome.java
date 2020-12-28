package neat.population.genome;

import neat.population.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome
{

    private final Population population;
    private final List<Gene> genes = new ArrayList<>();

    private final int inputs;
    private final int outputs;

    private int nodes;


    public Genome(Population population, int inputs, int outputs)
    {
        this.population = population;

        this.inputs = inputs;
        this.outputs = outputs;

        // Calculate number of nodes (Extra 1 is for bias node)
        nodes = inputs + outputs + 1;
        addGene();
    }

    private Genome(Genome genome)
    {
        population = genome.population;

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
        Random random = population.getRandom();

        // Mutate genes
        for (Gene gene : genes)
        {
            if (random.nextDouble() < Population.MUTATE_WEIGHT) gene.mutate();
        }

        // Add connections and nodes
        if (random.nextDouble() < Population.ADD_CONNECTION) addGene();
        if (random.nextDouble() < Population.ADD_NODE) addNode();
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

        Random random = population.getRandom();
        int in, out;

        do
        {
            // Select random node pair until one is valid;
            in = random.nextInt(nodes);
            out = random.nextInt(nodes);
        }
        while (isInvalid(in, out));

        genes.add(new Gene(population, in, out));
    }

    private boolean isInvalid(int in, int out)
    {
        // Tests if gene already exists
        for (Gene gene : genes)
        {
            if (gene.getIn() == in && gene.getOut() == out) return true;
        }

        return false;
    }

    private void addNode()
    {
        Random random = population.getRandom();

        // Select random gene
        Gene gene = genes.get(random.nextInt(genes.size()));
        gene.disable();

        int node = nodes++; // Create node

        // Create new connections
        genes.add(new Gene(population, gene.getIn(), node, 1));
        genes.add(new Gene(population, node, gene.getOut(), gene.getWeight()));
    }

}
