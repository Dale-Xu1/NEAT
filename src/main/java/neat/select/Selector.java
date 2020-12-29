package neat.select;

import java.util.List;
import java.util.Random;

public class Selector<T extends Selectable>
{

    private final Random random;
    private final List<T> list;


    public Selector(Random random, List<T> list)
    {
        this.random = random;
        this.list = list;
    }


    public T select()
    {
        // Calculate fitness sum
        double fitness = 0;
        for (T selectable : list)
        {
            fitness += selectable.getFitness();
        }

        // Select with equal distribution if all fitness' are 0
        if (fitness == 0) return list.get(random.nextInt(list.size()));

        // Select random number in distribution
        double rand = random.nextDouble() * fitness;
        double sum = 0;

        // Figure out which region number corresponds to
        for (T selectable : list)
        {
            sum += selectable.getFitness();

            if (sum > rand)
            {
                return selectable;
            }
        }

        // Unreachable
        throw new RuntimeException("Random number was larger than the fitness sum");
    }

}
