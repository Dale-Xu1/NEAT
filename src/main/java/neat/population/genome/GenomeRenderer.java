package neat.population.genome;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class GenomeRenderer
{

    private static class Node
    {
        private final double x;
        private final double y;

        public Node(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        public double getX()
        {
            return x;
        }

        public double getY()
        {
            return y;
        }
    }


    private static final double RADIUS = 10;

    private static final double MIN = 0.15;
    private static final double MAX = 0.85;


    private final List<Gene> genes;
    private final Node[] nodes;


    public GenomeRenderer(Genome genome)
    {
        nodes = new Node[genome.getNodes()];
        genes = genome.getGenes();

        // Create nodes
        createNodes(genome);
    }

    private void createNodes(Genome genome)
    {
        // Calculate positions of nodes
        int inputs = genome.getInputs();
        int outputs = genome.getOutputs();

        int n = 0;
        for (int i = 0; i < inputs; i++, n++)
        {
            Node node = new Node(MIN, (double) (i + 1) / (inputs + 2));
            nodes[n] = node;
        }
        nodes[n++] = new Node(MIN, 1 - 1 / (double) (inputs + 2));

        for (int i = 0; i < outputs; i++, n++)
        {
            Node node = new Node(MAX, (double) (i + 1) / (outputs + 1));
            nodes[n] = node;
        }

        int length = Math.max(inputs, outputs);
        for (; n < nodes.length; n++)
        {
            double space = 0.05;
            double margin = (double) 1 / (length + 1);

            nodes[n] = new Node(
                Math.random() * (MAX - MIN - space * 2) + MIN + space,
                Math.random() * (1 - 2 * margin) + margin
            );
        }
    }


    public void render(GraphicsContext gc, double x, double y, double w, double h)
    {
        gc.save();
        gc.translate(x, y);

        // Draw network
        renderConnections(gc, w, h);
        renderNodes(gc, w, h);

        gc.restore();
    }

    private void renderConnections(GraphicsContext gc, double w, double h)
    {
        for (Gene gene : genes)
        {
            Node in = nodes[gene.getIn()];
            Node out = nodes[gene.getOut()];

            // Set color and width
            double weight = gene.getWeight();
            double alpha = Math.abs(weight);

            gc.setStroke((weight > 0) ?
                new Color(1, 0, 0, alpha) :
                new Color(0, 0, 1, alpha));
            gc.setLineWidth(alpha * 3);

            if (in == out)
            {
                // Draw square to represent recurrent connection
                double height = RADIUS * 1.5;
                gc.strokeRect(in.getX() * w - height, in.getY() * h - height, height * 2, height);
            }
            else
            {
                // Draw line connecting nodes
                gc.strokeLine(in.getX() * w, in.getY() * h, out.getX() * w, out.getY() * h);
            }
        }
    }

    private void renderNodes(GraphicsContext gc, double w, double h)
    {
        for (int i = 0; i < nodes.length; i++)
        {
            Node node = nodes[i];

            gc.setFill(Color.WHITE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);

            // Calculate position
            double x = node.getX() * w;
            double y = node.getY() * h;

            double diameter = RADIUS * 2;

            // Draw circle with outline
            gc.fillOval(x - RADIUS, y - RADIUS, diameter, diameter);
            gc.strokeOval(x - RADIUS, y - RADIUS, diameter, diameter);

            // Write node index
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);

            gc.setFill(Color.BLACK);
            gc.fillText(Integer.toString(i), x, y);
        }
    }

}
