package neat.network;

import java.util.ArrayList;
import java.util.List;

public class Node
{

    private static double sigmoid(double value)
    {
        return 1 / (1 + Math.pow(Math.E, -4.9 * value));
    }


    private final List<Connection> connections = new ArrayList<>();

    private double value = 0.5;
    private boolean isEvaluated = false;


    public void addConnection(Connection connection)
    {
        connections.add(connection);
    }


    public void initialize()
    {
        isEvaluated = false;
    }

    public void initialize(double value)
    {
        // Initialize an input
        this.value = value;
        isEvaluated = true;
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

        // Pass through activation
        value = sigmoid(sum);
        return value;
    }

}
