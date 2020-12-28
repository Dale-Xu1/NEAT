package neat.population;

import neat.population.genome.Genome;
import neat.population.innovation.History;

public class Population
{

    private final History history = new History();


    public Genome getBest()
    {
        return new Genome(history, 3, 2);
    }

}
