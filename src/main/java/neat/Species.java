package neat;

import neat.genome.Gene;
import neat.genome.Genome;
import neat.select.Selectable;
import neat.select.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Species implements Selectable
{

    private final Random random;

    private final Genome representative;
    private final List<Genome> genomes = new ArrayList<>();

    private final Selector<Genome> selector;
    private double fitness;

    private boolean copyBest = false;


    public Species(NEAT neat, Genome representative)
    {
        random = neat.getRandom();
        this.representative = representative;

        selector = new Selector<>(random, genomes);
    }


    public Genome getBest()
    {
        Genome best = null;
        for (Genome genome : genomes)
        {
            // Find best genome
            if (best == null || genome.getFitness() > best.getFitness()) best = genome;
        }

        return best;
    }

    public Genome getChild()
    {
        if (copyBest)
        {
            // Copy best genome to next generation
            copyBest = false;
            return getBest();
        }

        // Create child
        Genome child = (random.nextDouble() < NEAT.NO_CROSSOVER) ? new Genome(selector.select()) : crossover();
        child.mutate(); // Wow this sounds weird

        return child;
    }

    private Genome crossover()
    {
        // Select parents
        Genome parent1 = selector.select();
        Genome parent2 = selector.select();

        if (parent2.getFitness() > parent1.getFitness())
        {
            // Swap so that parent1 is fitter
            Genome temp = parent1;

            parent1 = parent2;
            parent2 = temp;
        }

        // Copy parent and crossover with the other
        Genome child = new Genome(parent1);
        child.crossover(parent2);

        return child;
    }


    public void calculateFitness()
    {
        double sum = 0;
        for (Genome genome : genomes)
        {
            // Find sum of genome fitness
            sum += genome.getFitness();
        }

        // Take average
        fitness = sum / genomes.size();
    }

    @Override
    public double getFitness()
    {
        return fitness;
    }


    public void add(Genome genome)
    {
        genomes.add(genome);

        // Species is allowed to copy its best genome
        if (genomes.size() > NEAT.COPY_GENOME)
        {
            copyBest = true;
        }
    }

    public boolean isEmpty()
    {
        return genomes.isEmpty();
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

}
