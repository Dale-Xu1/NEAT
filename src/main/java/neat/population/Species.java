package neat.population;

import neat.population.genome.Gene;
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
        // Count number of matching genes
        int matching = 0;

        for (Gene gene : representative.getGenes())
        {
            // Loop through other genome to see if this gene exists there too
            for (Gene other : genome.getGenes())
            {
                if (gene.getInnovation() == other.getInnovation())
                {
                    matching++;
                    break;
                }
            }
        }

        // Total number of genes with the matching genes of each genome subtracted
        return (representative.getGenes().size() + genome.getGenes().size()) - (matching * 2);
    }

    private double weightDifference(Genome genome)
    {
        double difference = 0;
        int total = 0;

        for (Gene gene : representative.getGenes())
        {
            // Loop through other genome to see if this gene exists there too
            for (Gene other : genome.getGenes())
            {
                if (gene.getInnovation() == other.getInnovation())
                {
                    // Sum weight difference
                    difference += Math.abs(gene.getWeight() - other.getWeight());
                    total++;

                    break;
                }
            }
        }

        // Take average
        if (total == 0) return Double.MAX_VALUE; // No matching genes were found
        return difference / total;
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
