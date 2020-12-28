package neat.network;

import javafx.scene.canvas.GraphicsContext;
import neat.population.genome.Gene;
import neat.population.genome.Genome;

import java.util.List;

public class Network
{

    private final Node[] nodes;

    private final Node[] inputs;
    private final Node bias;

    private final Node[] outputs;


    public Network(Genome genome)
    {
        nodes = new Node[genome.getNodes()];

        inputs = new Node[genome.getInputs()];
        bias = Node.createBias(inputs.length); // Create bias node

        outputs = new Node[genome.getOutputs()];

        // Construct network from genome
        createNodes();
        createConnections(genome.getGenes());
    }

    private void createNodes()
    {
        // Create nodes
        int n = 0;
        for (int i = 0; i < inputs.length; i++, n++)
        {
            Node node = Node.createInput(i, inputs.length);

            inputs[i] = node;
            nodes[n] = node;
        }
        nodes[n++] = bias;

        for (int i = 0; i < outputs.length; i++, n++)
        {
            Node node = Node.createOutput(i, outputs.length);

            outputs[i] = node;
            nodes[n] = node;
        }

        for (; n < nodes.length; n++)
        {
            nodes[n] = new Node(Math.random(), Math.random());
        }
    }

    private void createConnections(List<Gene> genes)
    {
        // Initialize connections
        for (Gene gene : genes)
        {
            if (!gene.isEnabled()) continue; // Don't add disabled genes

            Node in = nodes[gene.getIn()];
            Node out = nodes[gene.getOut()];

            // Create connection
            Connection connection = new Connection(in, gene.getWeight());
            out.addConnection(connection);
        }
    }


    public void render(GraphicsContext gc, double x, double y, double w, double h)
    {
        gc.save();
        gc.translate(x, y);

        // Draw network
        for (Node node : nodes) node.renderConnections(gc, w, h);
        for (Node node : nodes) node.render(gc, w, h);

        gc.restore();
    }

}
