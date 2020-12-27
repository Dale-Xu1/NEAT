package neat.population.genome;

public class Connection
{

    private final int in;
    private final int out;

    private double weight;
    private final int innovation;


    public Connection(int in, int out, double weight)
    {
        this.in = in;
        this.out = out;

        this.weight = weight;
        this.innovation = 0;
    }

    public Connection(Connection connection)
    {
        in = connection.in;
        out = connection.out;

        weight = connection.weight;
        innovation = connection.innovation;
    }


    public void mutate()
    {

    }

}
