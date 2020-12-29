package neat.population;

import neat.population.genome.Genome;

import java.util.ArrayList;
import java.util.List;

public class Species
{

    private final Genome representative;
    private final List<Genome> genomes = new ArrayList<>();


    public Species(Genome representative)
    {
        this.representative = representative;
    }


    public Genome getBest()
    {
        // TODO: Find best genome
        return representative;
    }


    public boolean isCompatible(Genome genome)
    {
        // Larger gene length
        int normalizer = Math.max(representative.getGenes().size(), genome.getGenes().size()) - NEAT.MIN_NORMAL;
        if (normalizer < 1) normalizer = 1;

        // Calculate factors
        int disjoint = disjointGenes(genome);
        double weights = weightDifference(genome);

        // Calculate distance
        double distance = (NEAT.DISJOINT * disjoint) / normalizer + (NEAT.WEIGHTS * weights);
        return distance < NEAT.THRESHOLD;
    }

    private int disjointGenes(Genome genome)
    {
        return 0;
    }

    private double weightDifference(Genome genome)
    {
        return 0;
    }


    public void add(Genome genome)
    {
        genomes.add(genome);
    }

    public boolean isEmpty()
    {
        return genomes.isEmpty();
    }

}
