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
        // TODO: Calculate genome distance
        return false;
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
