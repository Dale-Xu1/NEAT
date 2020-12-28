package neat.network;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Node
{

    private static final double RADIUS = 10;

    private static final double MIN = 0.15;
    private static final double MAX = 0.85;


    public static Node createInput(int i, int length)
    {
        return new Node(MIN, (double) (i + 1) / (length + 2));
    }

    public static Node createBias(int length)
    {
        return createInput(length, length);
    }

    public static Node createOutput(int i, int length)
    {
        return new Node(MAX, (double) (i + 1) / (length + 1));
    }

    public static Node createHidden(int length)
    {
        double space = 0.05;
        double margin = (double) 1 / (length + 1);

        return new Node(
            Math.random() * (MAX - MIN - space * 2) + MIN + space,
            Math.random() * (1 - 2 * margin) + margin
        );
    }


    private final List<Connection> connections = new ArrayList<>();

    private final double x;
    private final double y;


    public Node(double x, double y)
    {
        this.x = x;
        this.y = y;
    }


    public void addConnection(Connection connection)
    {
        connections.add(connection);
    }


    public void renderConnections(GraphicsContext gc, double w, double h)
    {
        for (Connection connection : connections)
        {
            double weight = connection.getWeight();
            Node in = connection.getIn();

            // Set color and width
            double alpha = Math.abs(weight);

            gc.setStroke((weight > 0) ?
                new Color(1, 0, 0, alpha) :
                new Color(0, 0, 1, alpha));
            gc.setLineWidth(alpha * 3);

            if (in == this)
            {
                // Draw square to represent recurrent connection
                double height = RADIUS * 1.5;
                gc.strokeRect(x * w - height, y * h - height, height * 2, height);
            }
            else
            {
                // Draw line connecting nodes
                gc.strokeLine(in.x * w, in.y * h, x * w, y * h);
            }
        }
    }

    public void render(GraphicsContext gc, double w, double h)
    {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Calculate position
        double x = this.x * w - RADIUS;
        double y = this.y * h - RADIUS;

        double diameter = RADIUS * 2;

        // Draw circle with outline
        gc.fillOval(x, y, diameter, diameter);
        gc.strokeOval(x, y, diameter, diameter);
    }

}
