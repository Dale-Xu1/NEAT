package neat.network;

public class Connection
{

    private final Node in;
    private final double weight;


    public Connection(Node in, double weight)
    {
        this.in = in;
        this.weight = weight;
    }


    public Node getIn()
    {
        return in;
    }

    public double getWeight()
    {
        return weight;
    }

}