package neat.population.genome;

import neat.population.NEAT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome
{

    private final NEAT neat;
    private final List<Gene> genes = new ArrayList<>();

    private final int inputs;
    private final int outputs;

    private int nodes;


    public Genome(NEAT neat, int inputs, int outputs)
    {
        this.neat = neat;

        this.inputs = inputs;
        this.outputs = outputs;

        // Calculate number of nodes (Extra 1 is for bias node)
        nodes = inputs + outputs + 1;
        addGene();
    }

    public Genome(Genome genome)
    {
        neat = genome.neat;

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
        Random random = neat.getRandom();

        // Mutate genes
        for (Gene gene : genes)
        {
            if (random.nextDouble() < NEAT.MUTATE_WEIGHT) gene.mutate();
        }

        // Add connections and nodes
        if (random.nextDouble() < NEAT.ADD_CONNECTION) addGene();
        if (random.nextDouble() < NEAT.ADD_NODE) addNode();
    }

    public void crossover(Genome genome)
    {
        // Loop through genes
        for (Gene gene : genes)
        {
            // Test if gene is a matching gene
            for (Gene other : genome.genes)
            {
                if (gene.getInnovation() == other.getInnovation())
                {
                    // Crossover gene
                    gene.crossover(other);
                    break;
                }
            }
        }
    }


    private void addGene()
    {
        // Maximum gene length was reached
        if (genes.size() >= nodes * (nodes - inputs - 1)) return;

        Random random = neat.getRandom();
        int in, out;

        do
        {
            // Select random node pair until one is valid
            in = random.nextInt(nodes);
            out = random.nextInt(nodes);
        }
        while (isInvalid(in, out));

        genes.add(new Gene(neat, in, out));
    }

    private boolean isInvalid(int in, int out)
    {
        // Invalid if connection leads into input
        if (out <= inputs) return true;

        // Tests if gene already exists
        for (Gene gene : genes)
        {
            if (gene.getIn() == in && gene.getOut() == out) return true;
        }

        return false;
    }

    private void addNode()
    {
        Random random = neat.getRandom();

        // Select random gene
        Gene gene = genes.get(random.nextInt(genes.size()));
        gene.disable();

        int node = nodes++; // Create node

        // Create new connections
        genes.add(new Gene(neat, gene.getIn(), node, 1));
        genes.add(new Gene(neat, node, gene.getOut(), gene.getWeight()));
    }

}
