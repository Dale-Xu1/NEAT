package neat.population.genome;

import neat.population.innovation.History;

public class Connection
{

    private final int in;
    private final int out;

    private double weight;
    private final int innovation;


    public Connection(History history, int in, int out, double weight)
    {
        this.in = in;
        this.out = out;

        this.weight = weight;
        this.innovation = history.getNumber(in, out);
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
