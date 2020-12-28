package neat.population.genome.render;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import neat.population.genome.Gene;
import neat.population.genome.Genome;

import java.util.List;
import java.util.Random;

public class GenomeRenderer
{

    private static final int SEED = 0;
    private static final double RADIUS = 10;

    private static final double MIN = 0.1;
    private static final double MAX = 0.9;

    private static final double SPACE = 0.05;


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

        // Hidden nodes are placed randomly
        Random random = new Random(SEED);
        int length = Math.max(inputs, outputs);

        for (; n < nodes.length; n++)
        {
            double margin = (double) 1 / (length + 1);

            nodes[n] = new Node(
                random.nextDouble() * (MAX - MIN - SPACE * 2) + MIN + SPACE,
                random.nextDouble() * (1 - 2 * margin) + margin
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
            // Don't render if disabled
            if (gene.isDisabled()) continue;

            Node in = nodes[gene.getIn()];
            Node out = nodes[gene.getOut()];

            // Set color and width
            double weight = gene.getWeight();
            double alpha = Math.abs(weight);

            gc.setStroke((weight > 0) ?
                new Color(1, 0, 0, alpha) :
                new Color(0, 0, 1, alpha));
            gc.setLineWidth(alpha * 3 + 1);

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
//        for (Node node : nodes)
//        {
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);

            // Calculate position
            double x = node.getX() * w - RADIUS;
            double y = node.getY() * h - RADIUS;

            double diameter = RADIUS * 2;

            // Draw circle with outline
            gc.fillOval(x, y, diameter, diameter);
            gc.strokeOval(x, y, diameter, diameter);

            // Write node index
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);

            gc.setFill(Color.BLACK);
            gc.fillText(Integer.toString(i), x + RADIUS, y + RADIUS);
        }
    }

}
