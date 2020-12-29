package neat.network;

import java.util.ArrayList;
import java.util.List;

public class Node
{

    private final List<Connection> connections = new ArrayList<>();

    private double value = 0.5;
    private boolean isEvaluated = false;


    public void addConnection(Connection connection)
    {
        connections.add(connection);
    }


    public double evaluate()
    {
        // Don't evaluate if method was already called
        if (isEvaluated) return value;
        isEvaluated = true;

        // Evaluate connections
        double sum = 0;
        for (Connection connection : connections)
        {
            sum += connection.evaluate();
        }

        // TODO: Pass through activation
        return sum;
    }

}
