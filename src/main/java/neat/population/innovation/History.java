package neat.population.innovation;

import java.util.ArrayList;
import java.util.List;

public class History
{

    private final List<Innovation> innovations = new ArrayList<>();
    private int number = 0;


    public int getNumber(int in, int out)
    {
        // Use existing number if innovation has already occurred
        for (Innovation innovation : innovations)
        {
            if (innovation.matches(in, out))
            {
                return innovation.getNumber();
            }
        }

        // Store new innovation
        innovations.add(new Innovation(in, out, number));
        return number++;
    }

    public void clear()
    {
        // Clear for next generation
        innovations.clear();
    }

}
