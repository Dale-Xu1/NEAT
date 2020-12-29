package neat.network;

import neat.genome.Gene;
import neat.genome.Genome;

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
        outputs = new Node[genome.getOutputs()];

        // Construct network from genome
        for (int i = 0; i < nodes.length; i++)
        {
            nodes[i] = new Node();
        }

        // Organize node references
        int n = 0;
        for (int i = 0; i < inputs.length; i++, n++)
        {
            inputs[i] = nodes[n];
        }
        bias = nodes[n++];

        for (int i = 0; i < outputs.length; i++, n++)
        {
            outputs[i] = nodes[n];
        }

        // Initialize connections
        for (Gene gene : genome.getGenes())
        {
            if (gene.isDisabled()) continue; // Don't add disabled genes

            Node in = nodes[gene.getIn()];
            Node out = nodes[gene.getOut()];

            // Create connection
            Connection connection = new Connection(in, gene.getWeight());
            out.addConnection(connection);
        }
    }


    public double[] predict(double[] input)
    {
        if (input.length != inputs.length)
        {
            // Input lengths must match
            throw new RuntimeException("Expected " + inputs.length + " inputs but got " + input.length);
        }

        // Set up nodes
        for (Node node : nodes) node.initialize();

        // Initialize input values
        for (int i = 0; i < input.length; i++)
        {
            Node node = inputs[i];
            node.initialize(input[i]);
        }
        bias.initialize(1);

        // Evaluate outputs
        double[] output = new double[outputs.length];
        for (int i = 0; i < output.length; i++)
        {
            Node node = outputs[i];
            output[i] = node.evaluate();
        }

        return output;
    }

}
