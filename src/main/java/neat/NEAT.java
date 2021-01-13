package neat;

import neat.genome.Genome;
import neat.genome.innovation.History;
import neat.select.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NEAT
{

    public static final int MIN_NORMAL = 20; // Minimum gene length where normalizer is 1
    public static final int COPY_GENOME = 5; // Minimum genomes in a species for the best to be copied
    public static final double DISJOINT = 1; // How much disjoint genes contribute to distance
    public static final double WEIGHTS = 3; // How much weight differences contribute to distance
    public static final double THRESHOLD = 4; // Distance threshold for whether is genome is part of a species

    public static final double MUTATE_WEIGHT = 0.8; // Chance of mutating a weight
    public static final double RESET_WEIGHT = 0.1; // Chance of resetting a weight (When it is being mutated)
    public static final double SHIFT_WEIGHT = 0.05; // Coefficient of gaussian distribution when weight is shifted
    public static final double ADD_CONNECTION = 0.05; // Chance of a connection being added
    public static final double ADD_NODE = 0.03; // Chance of a node being added between a connection

    public static final double NO_CROSSOVER = 0.25; // Chance to mutate without crossover
    public static final double DISABLE_GENE = 0.75; // Chance a gene is disabled if it is disabled in either parent


    private final History history = new History();
    private final Random random;

    private final Genome[] genomes;
    private List<Species> species = new ArrayList<>();


    private NEAT(Random random, int population, int inputs, int outputs)
    {
        this.random = random;

        // Create initial population
        genomes = new Genome[population];
        for (int i = 0; i < population; i++)
        {
            genomes[i] = new Genome(this, inputs, outputs);
        }

        // Organize population into species
        speciate();
    }

    public NEAT(int population, int inputs, int outputs, long seed)
    {
        this(new Random(seed), population, inputs, outputs);
    }

    public NEAT(int population, int inputs, int outputs)
    {
        this(new Random(), population, inputs, outputs);
    }


    public History getHistory()
    {
        return history;
    }

    public Random getRandom()
    {
        return random;
    }

    public Genome[] getGenomes()
    {
        return genomes;
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


    public void nextGeneration()
    {
        // Create new genomes
        nextGenomes();

        // Create new species
        nextSpecies();
        speciate();

        // Clear innovations
        history.clear();
    }

    private void nextGenomes()
    {
        // Calculate average fitness of each species
        for (Species species : species)
        {
            species.calculateFitness();
        }

        // Create species selector
        Selector<Species> selector = new Selector<>(random, species);
        for (int i = 0; i < genomes.length; i++)
        {
            // Select random species and create child
            Species species = selector.select();
            Genome child = species.getChild();

            genomes[i] = child;
        }
    }

    private void nextSpecies()
    {
        List<Species> next = new ArrayList<>();

        for (Species species : species)
        {
            // Create new species with old species's best genome as representative
            Genome best = species.getBest();
            best.setFitness(0);

            next.add(new Species(this, best));
        }

        species = next;
    }

    private void speciate()
    {
        main:
        for (Genome genome : genomes)
        {
            for (Species species : species)
            {
                // Add genome to species if it is compatible
                if (species.isCompatible(genome))
                {
                    species.add(genome);
                    continue main;
                }
            }

            // Create new species with this genome as the representative
            Species species = new Species(this, genome);
            species.add(genome);

            this.species.add(species);
        }

        // Remove empty species
        species.removeIf(Species::isEmpty);
    }

}
