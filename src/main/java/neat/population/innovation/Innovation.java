package neat.population.innovation;

public class Innovation
{

    private final int in;
    private final int out;

    private final int number;


    public Innovation(int in, int out, int number)
    {
        this.in = in;
        this.out = out;

        this.number = number;
    }


    public boolean matches(int in, int out)
    {
        return this.in == in && this.out == out;
    }

    public int getNumber()
    {
        return number;
    }

}
